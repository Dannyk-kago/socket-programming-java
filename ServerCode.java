import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;
import java.io.File;

public class ServerCode{
    public static void main(String[] args){
        try{
            //Create socket server at port 2030
            ServerSocket ss = new ServerSocket(2030);

            //Acceptance
            Socket s = ss.accept();

            //Get input output stream
            InputStream is = s.getInputStream();
            OutputStream os = s.getOutputStream();

            //convert to scanner and printer
            Scanner sc = new Scanner(is);
            PrintWriter pw  = new PrintWriter(os);

            //Receive command from client
            String command = sc.nextLine();

            //Detach file name
            String fileName = command.substring(5);

            //chech file
            File f = new File(fileName);
            if(f.exists() && f.isFile()){
                int size = (int)f.length();
                pw.print(size);
                pw.flush();
                if(size > 0){
                    FileInputStream fis = new FileInputStream(f);
                    DataInputStream dis = new DataInputStream(fis);
                    byte b[] = new byte[size];
                    dis.readFully(b);
                    System.out.println("Read file success");
                    fis.close();
                    //send file to client
                    DataOutputStream dos = new DataOutputStream(os);
                    dos.write(b);
                    System.out.println("Send file success");
                
                }
            }else {
                pw.println(-1);
                pw.flush();
            }
        }catch(SocketException e) {
            System.out.println("Socket error: " +e);
        }catch(FileNotFoundException e) {
            System.out.println("File not found: " +e);
        }catch(IOException e) {
            System.out.println("IO Exception: " +e);
        }
    }
}