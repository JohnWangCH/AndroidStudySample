package selftest.susample;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.channels.FileChannel;


public class ExecCmd {

    private static class Worker extends Thread {
        private final Process process;
        private Integer exit;

        private Worker(Process process) {
            this.process = process;
        }

        public void run() {
            try {
                exit = process.waitFor();
            } catch (InterruptedException ignore) {
                return;
            }
        }
    }

    public static void suexecShell(String cmd) {

        try {
            Process process = Runtime.getRuntime().exec("su");
            DataOutputStream dataOutputStream = new DataOutputStream(process.getOutputStream());
            dataOutputStream.writeBytes(cmd);
            dataOutputStream.flush();
            dataOutputStream.close();
            process.waitFor();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void shexecShell(String cmd) {

        try {
            Process process = Runtime.getRuntime().exec("sh");
            DataOutputStream dataOutputStream = new DataOutputStream(process.getOutputStream());
            dataOutputStream.writeBytes(cmd);
            dataOutputStream.flush();
            dataOutputStream.close();
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static String suResultExeCmd(String cmd, int timeout_milliseconds){
        String[] cmdStrings = new String[] { "su", "-c", cmd };
        String retString = "";
        try {

            Process process = Runtime.getRuntime().exec(cmdStrings);
            Worker worker = new Worker(process);
            worker.start();
            worker.join(timeout_milliseconds);
            if(worker.exit != null)
            {
                BufferedReader stdout = new BufferedReader(new InputStreamReader(process.getInputStream()), 7777);
                BufferedReader stderr = new BufferedReader(new InputStreamReader(process.getErrorStream()), 7777);

                String line = null;

                while ((null != (line = stdout.readLine())) || (null != (line = stderr.readLine()))) {
                    //retString += line + "\n";
                    retString += line;
                }
            }
            else
                throw new Exception("Time out for command: " + cmd);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return retString;
    }


    public static String shResultExeCmd(String cmd, int timeout_milisecs) {
        String[] cmdStrings = new String[] { "sh", "-c", cmd };
        String retString = "";

        try {
            Process process = Runtime.getRuntime().exec(cmdStrings);
            Worker worker = new Worker(process);
            worker.start();
            worker.join(timeout_milisecs);
            if(worker.exit != null) {
                BufferedReader stdout = new BufferedReader(new InputStreamReader(process.getInputStream()), 7777);
                BufferedReader stderr = new BufferedReader(new InputStreamReader(process.getErrorStream()), 7777);

                String line = null;

                while ((null != (line = stdout.readLine())) || (null != (line = stderr.readLine()))) {
                    //retString += line + "\n";
                    retString += line;
                }
            }
            else
                throw new Exception("Timeout for comand" + cmd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retString;
    }

    public static String[] resultArrayExeCmd(String cmd) {
        int MaxLineNum = 40;
        String[] cmdStrings = new String[] { "sh", "-c", cmd };
        String[] retString = new String[MaxLineNum];
        int i=0;
        try {
            Process process = Runtime.getRuntime().exec(cmdStrings);
            BufferedReader stdout = new BufferedReader(new InputStreamReader(process.getInputStream()), 7777);
            BufferedReader stderr = new BufferedReader(new InputStreamReader(process.getErrorStream()), 7777);

            String line = null;

            while ((null != (line = stdout.readLine()))
                    || (null != (line = stderr.readLine()))) {
                if(i < MaxLineNum-1){
                    retString[i+1] = line ;
                    i++;
                    //retString += line;
                }
            }
            //retString[0] is for saving the line number of results
            retString[0] = i + "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retString;
    }

    public static void Copy(String srcpath, String dstpath) throws IOException {
        FileInputStream inStream = new FileInputStream(new File(srcpath));
        FileOutputStream outStream = new FileOutputStream(new File(dstpath));
        FileChannel inChannel = inStream.getChannel();
        FileChannel outChannel = outStream.getChannel();
        inChannel.transferTo(0, inChannel.size(), outChannel);
        inStream.close();
        outStream.close();
    }


}
