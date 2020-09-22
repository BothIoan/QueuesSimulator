import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
        public static void main(String[] args) throws IOException {
            System.out.println("type 1 for UI, type 2 for textBased");
            int c;
                Scanner sc = new Scanner(System.in);
                c = sc.nextInt();
            if(c == 1)
            {
            UI ui = new UI();
            return;
            }
            if(c == 2)
            {
                FileFunctions fileFunctions1 = new FileFunctions();
                FileFunctions fileFunctions2 = new FileFunctions();
                FileFunctions fileFunctions3 = new FileFunctions();
                System.out.println("type 1 for staticly generated Clients (like in the requirements) or 2 for dinamically generated clients");
                if(sc.nextInt()== 1)
                {

                    Shop shop1 = new Shop((ArrayList<Integer>) fileFunctions1.readFromFile("in-test-1"),"out1",true,fileFunctions1);
                    Shop shop2 = new Shop((ArrayList<Integer>) fileFunctions2.readFromFile("in-test-2"),"out2",true,fileFunctions2);
                    Shop shop3 = new Shop((ArrayList<Integer>) fileFunctions3.readFromFile("in-test-3"),"out3",true,fileFunctions3);
                }
                else{
                    Shop shop1 = new Shop((ArrayList<Integer>) fileFunctions1.readFromFile("in-test-1"),"out1",false,fileFunctions1);
                    Shop shop2 = new Shop((ArrayList<Integer>) fileFunctions2.readFromFile("in-test-2"),"out2",false,fileFunctions2);
                    Shop shop3 = new Shop((ArrayList<Integer>) fileFunctions3.readFromFile("in-test-3"),"out3",false,fileFunctions3);
                }
                return;
            }
        }
}
