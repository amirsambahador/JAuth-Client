package org.j2os.jauth.common.infrastructure;


import java.io.*;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.*;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class OS {
    private OS() {
    }

    public static List<Serializable> deSerialize(String input) throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(input);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        List<Serializable> list = (List<Serializable>) objectInputStream.readObject();
        objectInputStream.close();
        fileInputStream.close();
        return list;
    }

    public static void serialize(List<Serializable> list, String output) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(output);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(list);
        objectOutputStream.close();
        fileOutputStream.close();
    }

    public static void writeFile(String content, String output) throws IOException {
        File file = new File(output);
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(content);
        fileWriter.close();
    }

    public static String readFile(String input) throws IOException {
        File file = new File(input);
        FileReader fileReader = new FileReader(file);
        int ascii = fileReader.read();
        String content = "";
        while (ascii != -1) {
            content += (char) ascii;
            ascii = fileReader.read();
        }
        fileReader.close();
        return content;
    }


    public static String post(String webAddress, String body) throws IOException {
        URL url = new URL(webAddress);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        try (DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream())) {
            dataOutputStream.write(body.getBytes(StandardCharsets.UTF_8));
        }
        StringBuilder content = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"))) {
            String line = bufferedReader.readLine();
            while (line != null) {
                content.append(line);
                content.append(System.lineSeparator());
                line = bufferedReader.readLine();
            }
        }
        httpURLConnection.disconnect();
        return content.toString();
    }

    public static String post(String webAddress, String body, Map<String, String> headers) throws IOException {
        URL url = new URL(webAddress);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        for (String headerName : headers.keySet()) {
            httpURLConnection.setRequestProperty(headerName, headers.get(headerName));
        }
        try (DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream())) {
            dataOutputStream.write(body.getBytes(StandardCharsets.UTF_8));
        }
        StringBuilder content = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"))) {
            String line = bufferedReader.readLine();
            while (line != null) {
                content.append(line);
                content.append(System.lineSeparator());
                line = bufferedReader.readLine();
            }
        }
        httpURLConnection.disconnect();
        return content.toString();
    }

    public static String post(String webAddress, Map<String, String> headers) throws IOException {
        URL url = new URL(webAddress);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        for (String headerName : headers.keySet()) {
            httpURLConnection.setRequestProperty(headerName, headers.get(headerName));
        }
        StringBuilder content = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"))) {
            String line = bufferedReader.readLine();
            while (line != null) {
                content.append(line);
                content.append(System.lineSeparator());
                line = bufferedReader.readLine();
            }
        }
        httpURLConnection.disconnect();
        return content.toString();
    }

    public static String get(String webAddress, Map<String, String> headers) throws IOException {
        URL url = new URL(webAddress);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        for (String headerName : headers.keySet()) {
            httpURLConnection.setRequestProperty(headerName, headers.get(headerName));
        }
        StringBuilder content = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"))) {
            String line = bufferedReader.readLine();
            while (line != null) {
                content.append(line);
                content.append(System.lineSeparator());
                line = bufferedReader.readLine();
            }
        }
        httpURLConnection.disconnect();
        return content.toString();
    }

    public static String get(String webAddress) throws IOException {
        URL url = new URL(webAddress);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        StringBuilder content = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"))) {
            String line = bufferedReader.readLine();
            while (line != null) {
                content.append(line);
                content.append(System.lineSeparator());
                line = bufferedReader.readLine();
            }
        }
        httpURLConnection.disconnect();
        return content.toString();
    }

    public static void download(String webAddress, String output) throws IOException {
        URL url = new URL(webAddress);
        ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());
        FileOutputStream fileOutputStream = new FileOutputStream(output);
        FileChannel fileChannel = fileOutputStream.getChannel();
        fileChannel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
        fileChannel.close();
        fileOutputStream.close();
        readableByteChannel.close();
    }

    public static String getIP() throws UnknownHostException {
        InetAddress ip = InetAddress.getLocalHost();
        return ip.getHostAddress();
    }

    public static String getMAC() throws UnknownHostException, SocketException {
        InetAddress ip = InetAddress.getLocalHost();
        NetworkInterface network = NetworkInterface.getByInetAddress(ip);
        byte[] mac = network.getHardwareAddress();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < mac.length; i++) {
            stringBuilder.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
        }
        return stringBuilder.toString();
    }

    public static Map<String, String> getSystemProperties() {
        Map<String, String> map = new HashMap<>();
        Properties properties = System.getProperties();
        for (Object key : properties.keySet()) {
            map.put(String.valueOf(key), System.getProperty(String.valueOf(key)));
        }
        return map;
    }

    public static String getWebApplicationContextRoot() {
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        return classLoader.getResource("/WEB-INF/").getFile();
    }

    public static String getApplicationContextRoot() {
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        return classLoader.getResource(".").getFile();
    }

    public static List<String> getDriveName() {
        File[] files = File.listRoots();
        List<String> list = new ArrayList<>();
        for (File file : files) {
            list.add(file.toString());
        }
        return list;
    }

    public static Map<String, String> getProcessorInformation() {
        Map<String, String> map = new HashMap<>();
        OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
        for (Method method : operatingSystemMXBean.getClass().getMethods()) {
            method.setAccessible(true);
            if (method.getName().startsWith("get")
                    && Modifier.isPublic(method.getModifiers())) {
                Object methodReturn;
                try {
                    methodReturn = method.invoke(operatingSystemMXBean);
                } catch (Exception e) {
                    methodReturn = e;
                }
                map.put(method.getName(), String.valueOf(methodReturn));
            }
        }
        return map;
    }


    public static long getFreeMemory() {
        return Runtime.getRuntime().freeMemory();
    }

    public static long getTotalMemory() {
        return Runtime.getRuntime().totalMemory();
    }

    public static long getMaxMemory() {
        return Runtime.getRuntime().maxMemory();
    }

    public static long getNumberOfProcessors() {
        return Runtime.getRuntime().availableProcessors();
    }

    public static void gc() {
        System.gc();
    }

    public static void println(String color, Object o) {
        System.out.println(color + o);
    }

    public static void print(String color, Object o) {
        System.out.println(color + o);
    }

}
