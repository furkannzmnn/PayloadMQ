package org.example.consumer;

import org.example.broker.BrokerCluster;

public class Listener extends BrokerCluster{
    public void listen() {
        while (true) {
            String message = receive();
            for (String method : registerMethods) {
                // invoke method
                Class<?> clazz = null;
                try {
                    clazz = Class.forName(method);
                    clazz.newInstance();
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                    System.out.println("errror");
                }

                // execute class main method
                try {
                    clazz.getMethod("ok", String.class).invoke(clazz.newInstance(), message);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                registerMethods.poll();
            }
        }
}
}
