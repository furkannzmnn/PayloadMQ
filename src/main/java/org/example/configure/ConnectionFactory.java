package org.example.configure;

public class ConnectionFactory {
    private String host;
    private int port;
    private String username;
    private String password;
    private String virtualHost;
    private String queueName;
    private String consumerHost;
    public ConnectionFactory() {
        this.host = "localhost";
        this.port = 8080;
        this.username = "guest";
        this.password = "guest";
        this.virtualHost = "/";
        this.queueName = "test";
    }


    public ConnectionFactory createConsumerConfig() {
        this.consumerHost = "localhost";
        return this;
    }

    public static Builder createConnection() {
        return new Builder();
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getVirtualHost() {
        return virtualHost;
    }

    public String getQueueName() {
        return queueName;
    }

    public static final class Builder {
        private String host;
        private int port;
        private String username;
        private String password;
        private String virtualHost;
        private String queueName;

        private Builder() {
        }

        public Builder host(String host) {
            this.host = host;
            return this;
        }

        public Builder port(int port) {
            this.port = port;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder virtualHost(String virtualHost) {
            this.virtualHost = virtualHost;
            return this;
        }

        public Builder queueName(String queueName) {
            this.queueName = queueName;
            return this;
        }

        public ConnectionFactory build() {
            ConnectionFactory connectionFactory = new ConnectionFactory();
            connectionFactory.virtualHost = this.virtualHost;
            connectionFactory.queueName = this.queueName;
            connectionFactory.username = this.username;
            connectionFactory.password = this.password;
            connectionFactory.port = this.port;
            connectionFactory.host = this.host;
            return connectionFactory;
        }
    }
}
