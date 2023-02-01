import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import commands.*;
import core.*;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(System.in);
        System.out.print("Please input the file pathname: ");
        String filepathname = in.nextLine();
        Scanner inFile = new Scanner(new File(filepathname));
        // Company co = Company.getInstance();
        String cmdLine = inFile.nextLine();
        System.out.println("\n> " + cmdLine);
        SystemDate.createTheInstance(cmdLine.split("\\|")[1]);
        while (inFile.hasNext()) {
            cmdLine = inFile.nextLine();
            if (cmdLine.equals(""))
                continue;

            System.out.println("\n> " + cmdLine);

            String cmdParts[] = cmdLine.split("\\|");
            for (int i = 0; i < cmdParts.length; i++) {
                cmdParts[i] = cmdParts[i].trim();// Remove extra spaces in input argument bruh
            }
            switch (cmdParts[0]) {
                case "startNewDay":
                    (new CmdStartNewDay()).execute((cmdParts));
                    break;
                case "hire":
                    (new CmdHire()).execute(cmdParts);
                    break;
                case "listEmployees":
                    (new CmdListEmployees()).execute(cmdParts);
                    break;
                case "setupTeam":
                    (new CmdSetupTeam()).execute(cmdParts);
                    break;
                case "listTeams":
                    (new CmdListTeams()).execute(cmdParts);
                    break;
                case "addTeamMember":
                    (new CmdAddTeamMember()).execute(cmdParts);
                    break;
                case "listTeamMembers":
                    (new CmdListTeamMembers()).execute(cmdParts);
                    break;
                case "listRoles":
                    (new CmdListRoles()).execute(cmdParts);
                    break;
                case "undo":
                    RecordedCommand.undoOneCommand();
                    break;
                case "redo":
                    RecordedCommand.redoOneCommand();
                    break;
                default:
                    System.out.println("Unknown command");
            }
        }

        inFile.close();
        in.close();
    }
}