package com.statful.client.transport;

import com.statful.client.core.transport.TransportSender;

import java.io.IOException;
import java.net.*;
import java.nio.charset.Charset;
import java.util.logging.Logger;

/**
 * This class is an implementation of {@link com.statful.client.core.transport.TransportSender} to send metrics
 * using UDP.
 */
public class UDPSender implements TransportSender {

    private static final Logger LOGGER = Logger.getLogger(UDPSender.class.getName());

    private final int port;
    private final String host;

    private InetAddress address;
    private DatagramSocket socket;

    /**
     * Default constructor.
     *
     * @param host The hostname of the UDP server
     * @param port The port of the UDP server
     */
    public UDPSender(final String host, final int port) {
        this.port = port;
        this.host = host;

        try {
            createSocket();
        } catch (Exception e) {
            LOGGER.warning("Unable to open UDP socket: " + e.toString());
        }
    }

    @Override
    public final void send(final String message) {
        try {
            sendMessage(message);
        } catch (IOException e) {
            LOGGER.warning("I/O exception while sending message.");
        }
    }

    @Override
    public final void send(final String message, final String uri) { }

    private void sendMessage(final String message) throws IOException {
        try {
            createSocketIfClosed();
            socket.send(createPacket(message));
        } catch (SocketException e) {
            LOGGER.warning("Unable to open UDP socket: " + e.toString());
        } catch (UnknownHostException e) {
            LOGGER.warning("Unable to open UDP socket: " + e.toString());
        }
    }

    @Override
    public final void shutdown() {
        socket.disconnect();
        socket.close();
    }

    private void createSocketIfClosed() throws SocketException, UnknownHostException {
        if (socket == null || socket.isClosed()) {
            createSocket();
        }
    }

    private void createSocket() throws UnknownHostException, SocketException {
        address = InetAddress.getByName(host);
        socket = new DatagramSocket();
    }

    private DatagramPacket createPacket(final String message) {
        byte[] byteMessage = message.getBytes(Charset.forName("UTF-8"));
        return new DatagramPacket(byteMessage, byteMessage.length, address, port);
    }

    /**
     * Setter for datagram socket.
     *
     * @param socket A {@link java.net.DatagramSocket}
     */
    final void setSocket(final DatagramSocket socket) {
        this.socket = socket;
    }
}
