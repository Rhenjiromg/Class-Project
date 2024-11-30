//include this everywhere you are. Include the root folder (client, resouces, server)
package server;

//include this to access the class ResourcesTest in shared folder. 
//also works: shared.* for all imports (if there are many imports)
import shared.ResourcesTest;

public class ServerTest{

    public static void main(String[] args) {
        ResourcesTest.testGetResources();
    }
}