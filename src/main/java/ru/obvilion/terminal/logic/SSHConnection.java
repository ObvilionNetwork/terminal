package ru.obvilion.terminal.logic;

import com.jcraft.jsch.*;
import javafx.scene.Node;

import java.io.*;
import java.util.Properties;

public class SSHConnection {
    private final JSch jsch;
    private Session session;
    private Node node;

    public final String user;
    public final String host;
    public final int port;

    public final File keyFile;
    public final String password;

    public PipedOutputStream pipe = new PipedOutputStream();
    public InputStream in;
    public ByteArrayOutputStream out;

    public Channel channel;

    public SSHConnection(String user, String host, int port, String password, File keyFile) {
        this.jsch = new JSch();

        this.user = user;
        this.host = host;
        this.port = port;

        this.keyFile = keyFile;
        this.password = password;
    }

    public SSHConnection(String user, String host, int port, File key) {
        this(user, host, port, null, key);
    }

    public SSHConnection(String user, String host, int port, String password) {
        this(user, host, port, password, null);
    }

    public SSHConnection(String user, String host, int port) {
        this(user, host, port, null, null);
    }

    public void setInputNode(Node node) {
        this.node = node;
    }

    public void open() throws Exception {
        session = jsch.getSession(user, host, port);

        if (keyFile != null && password != null) {
            jsch.addIdentity(keyFile.getAbsolutePath(), password);
        }
        else if (password != null) {
            session.setPassword(password);
        }
        else if (keyFile != null) {
            jsch.addIdentity(keyFile.getAbsolutePath());
        }

        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");

        session.setConfig(config);
        session.connect();

        pipe = new PipedOutputStream();
        in = new PipedInputStream(pipe);
        out = new ByteArrayOutputStream();

        channel = session.openChannel("shell");
        channel.setInputStream(in);
        channel.setOutputStream(out);

        channel.connect();
    }

    public void sendCommand(String cmd) {
        try {
            pipe.write((cmd + "\n").getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}