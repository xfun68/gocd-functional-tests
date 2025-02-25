package com.thoughtworks.cruise.preconditions;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.thoughtworks.cruise.Agents;
import com.thoughtworks.cruise.RuntimePath;
import com.thoughtworks.cruise.Urls;
import com.thoughtworks.cruise.util.ExceptionUtils;
import com.thoughtworks.cruise.util.FileUtil;
import com.thoughtworks.cruise.util.SystemUtil;

public class AgentLauncher extends ProcessIsRunning {
	public static final File TWIST_AGENTS_DIR = new File(RuntimePath.pathFor("../target/twist-agents"));

    private static int agentCounter = 0;

	private File dir;

	private String uuid;
	

	public static AgentLauncher startNewAgent(String directory, String srcDir) throws Exception {
        return startAgentWithUUID(directory, null, srcDir);
    }
    
    public static AgentLauncher startAgentWithUUID(String agentDirectory, String uuid, String srcDir) throws Exception {
        AgentLauncher agent = create(agentDirectory, (srcDir == null) ? RuntimePath.getAgentRoot() : srcDir);
        if (uuid != null) {
            agent.useUUID(uuid);
        }
        agent.start();
        return agent;
    }

	private void useUUID(String uuid) {
	    try {
            FileUtils.writeStringToFile(uuidFile(), uuid);
        } catch (Exception e) {
            ExceptionUtils.bomb(e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AgentLauncher that = (AgentLauncher) o;

        if(uuid != null ? !uuid.equals(that.uuid) : that.uuid != null){
            return false;
        }
        if(dir != null ? !dir.equals(that.dir) : that.dir != null){
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = uuid != null ? uuid.hashCode() : 0;
        result = 31 * result + (dir != null ? dir.hashCode() : 0);
        return result;
    }

    private File uuidFile() {
        return new File(dir.getAbsolutePath() , "/config/guid.txt");
    }

    private static AgentLauncher create(String directory, String srcDir) throws IOException {
        File dir = new File(TWIST_AGENTS_DIR, newAgentDirectoryName(directory));
		if (SystemUtil.isWindows()) {
			if (!dir.exists()) {
				System.err.println("Agent not found. Copying across the agent installation");
				FileUtils.copyDirectory(new File(RuntimePath.getAgentRoot()), dir, new FileFilter() {
					public boolean accept(File pathname) {
						String path = pathname.getPath();
						return !path.endsWith("agent-bootstrapper.running") && 
								!path.endsWith("process.pid") && 
								!path.endsWith("guid.txt") &&
								!path.endsWith(".log");
					}
				});
				copyAgentLog4jProperties(dir);
			}
		} else {
			if (!dir.exists() || !new File(dir, "start-twist-agent.sh").exists()) {
				System.err.println("Agent not found. Copying across the agent installation into: " + dir);

				dir.mkdir();
				FileUtils.copyFileToDirectory(new File(srcDir, "agent-bootstrapper.jar"), dir);
				FileUtils.copyURLToFile(AgentLauncher.class.getResource("start-twist-agent.sh"), new File(dir, "start-twist-agent.sh"));
				FileUtils.copyURLToFile(AgentLauncher.class.getResource("stop-twist-agent.sh"), new File(dir, "stop-twist-agent.sh"));
				copyAgentLog4jProperties(dir);
			}
		}
		if (new File(dir, ".agent-bootstrapper.running").exists()) {
			throw new RuntimeException("Agent already running in " + dir.getPath());
		}
		FileUtils.deleteQuietly(new File(dir, "config"));
		FileUtils.deleteQuietly(new File(dir, "pipelines"));
        FileUtils.deleteQuietly(new File(dir, Agents.WAITING_FOR_STOP_JOB_FILE));
        FileUtils.deleteQuietly(new File(dir, Agents.STOP_JOB_FILE));
		AgentLauncher agent = new AgentLauncher(dir);
        return agent;
    }

	private static String newAgentDirectoryName(String directory) {
		return String.format("%s-%02d", directory, (++agentCounter));
	}

	private static void copyAgentLog4jProperties(File dir) throws IOException {
		File agentLog4j = new File(dir, "log4j.properties");
		FileUtils.copyFile(new File(RuntimePath.pathFor("properties"), "agent-log4j.properties"), agentLog4j);
	}
	
	public AgentLauncher(File dir) {
        this.dir = dir;
	}

	protected boolean isProcessStopped() {
        if (SystemUtil.isWindows()) {
            return isProcessStoppedOnWindows("cruise agent - agent.cmd");
        } else {
            return isProcessStoppedOnLinux();
        }
    }

    public void start() throws Exception {
        try {
            super.start();
            CleanupScenario.stopAgentOnShutdown(this);
        } catch (Error e) {
            // Twist doesn't do this for us so we lose assertion failure information :(
            System.err.println("Caught an exception during startup: ");
            e.printStackTrace();
            throw e;
        }
    }

    protected String startCommand() {
        return SystemUtil.isWindows() ? "start-agent.bat" : "./start-twist-agent.sh"; 
    }
    
    protected String stopCommand() {
        return SystemUtil.isWindows() ? "stop-agent.bat" : "./stop-twist-agent.sh"; 
    }

    protected String getWorkingDir() {
        return dir.getPath();
    }

    protected Map<String, String> getStartEnvVariables() {
        Map<String, String> env = new HashMap<String, String>();
        env.put("GO_SERVER", "127.0.0.1");
        env.put("VNC", "N");
        env.put("STOP_BEFORE_STARTUP", "N"); //else when test agent start it will kill "real" agent
        env.put("PRODUCTION_MODE", "N");
        env.put("GO_SERVER_PORT", Urls.SERVER_PORT);
        env.put("GO_SERVER_SSH_PORT", Urls.SSL_PORT);
        //env.put("JVM_DEBUG", "Y"); //uncomment to debug(use deligently, as second agent will not get a bind)
        env.put("GO_AGENT_SYSTEM_PROPERTIES", 
        		" -Dagent.get.work.delay=500" +
        		" -Dagent.get.work.interval=500" +
        		" -Dagent.ping.delay=500" +
        		" -Dagent.ping.interval=500");
        return env;
    }

	@Override
	protected boolean pumpOutputStream() {
		return true;
	}

	@Override
	protected boolean pumpErrorStream() {
		return true;
	}

    public String getWorkingDirectory() {
        return dir.getAbsolutePath();
    }

	public String getUuid() {
		if (uuid == null) {
			try {
				uuid = FileUtils.readFileToString(new File(dir, "config/guid.txt"));
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return uuid;
	}

    public boolean doesFileExist(String waitingForFilename) {
        return new File(dir, waitingForFilename).exists();
    }

	public void createFile(String name, boolean shouldPass) {
		try {
			String content = "";
			if (!shouldPass) {
				content += "\nstopjob.fail=ANYTHING\n";
			}
			FileUtil.writeContentToFile(content, new File(dir, name).getAbsoluteFile());
		} catch (IOException e) {
			throw new RuntimeException("Could not create file " + name, e);
		}
	}

	public void deleteFile(String name) {
		if (!new File(dir, name).delete()) {
			System.err.println("could not delete stopjob file: " + name);
		}
	}

    public void deleteUUID() {
        FileUtils.deleteQuietly(uuidFile());
    }

	public static void cleanup() {
		ProcessIsRunning.pkill("twist-agents");
		FileUtils.deleteQuietly(TWIST_AGENTS_DIR);
	}

    public void deleteDirectory() {
        dir.delete();        
    }

}

