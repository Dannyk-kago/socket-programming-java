import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class ClientCode{
    public static void main(String[] args){
        try{
            //Enter address and file location
            Scanner kb = new Scanner (System.in);
            System.out.print("Enter ip address:");
            String ip = kb.nextLine();
            System.out.print("Enter file Location:");
            String filePath = kb.nextLine();

            //Create socket at port 2030
            Socket s = new Socket(ip, 2030);
            
            //Get Stream in out
            InputStream is = s.getInputStream();
            OutputStream os = s.getOutputStream();

            //Convert to Scanner and Print Writer
            Scanner sc = new Scanner(is);
            PrintWriter pw = new PrintWriter(os);
            
            //Send Read Command
            String command = "READ" +filePath;
            pw.println(command);
            pw.flush();

            //Receive file size
            String sizeStr = sc.nextLine();
            int size = Integer.parseInt(sizeStr);
            if(size == -1){
                System.out.println("File" +filePath+ ": Not found");
            }else if(size == 0){
                System.out.println("File" +filePath+ ": Empty");
            }else {
                //Handle if has file and content
                System.out.print("Enter new location to save: ");
                String newFileLoca = kb.nextLine();
                FileOutputStream fos = new FileOutputStream(newFileLoca);
                byte b[] = new byte[10000];
                int sum = 0;

                DataInputStream dis = new DataInputStream(is);

                while(true){
                    int n = dis.read(b,0,10000);
                    fos.write(b,n,0);
                    sum += n;
                    System.out.println(sum+"Bytes Downloaded");
                    if(sum == size) break;
                }
                System.out.println("Download File Successfully");
                fos.close();
                dis.close();
            }
        }catch(UnknownHostException e){
            System.out.println("Can't connect to ip address:" +e);
        }catch(FileNotFoundException e){
            System.out.println("File not found:" +e);
        }catch(IOException e){
            System.out.println("Io Exception:" +e);
        }
    }
}