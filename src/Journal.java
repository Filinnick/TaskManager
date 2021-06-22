import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Journal {

    Actions actions = new Actions();

    private ArrayList<Task> tasks = new ArrayList();

    private int tasksNumber = 0;

    public static final String filePath = "D:/Programming/TaskManager/Journal.txt";

    File journalFile = new File(filePath);

    public void journalStart(){

        if(!journalFile.isFile()){
            try {
                journalFile.createNewFile();
            } catch (IOException e) {
                System.out.println("There is something wrong with CREATING of a Journal file!");
            }
        }

    }

    public ArrayList<Task> journalLoad() {

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        String currentLine;

        try {
            Reader reader = new FileReader(filePath);
            BufferedReader buffReader = new BufferedReader(reader);

            while (buffReader.ready()) {
                currentLine = buffReader.readLine();
                if(!(currentLine.equals(""))) {
                    Task task = new Task();
                    task.setName(currentLine.split("#")[0]);
                    task.setBody(currentLine.split("#")[1]);
                    task.setDateOfAlarm(dateFormat.parse(currentLine.split("#")[2]));
                    task.setContacts(currentLine.split("#")[3]);
                    task.setId(Integer.parseInt(currentLine.split("#")[4]));
                    tasks.add(task);
                    tasksNumber = task.getId();
                }
            }

            reader.close();
            buffReader.close();
        } catch (IOException e) {
            System.out.println("There is something wrong with READING from a Journal file!");
        } catch (ParseException e) {
            System.out.println("There is something wrong with date format!" );
        }
        //TODO передача тасков в аларм,
        return tasks;
    }

    public int getTasksNumber() {
        return tasksNumber;
    }

    public void setTasksNumber(int tasksNumber) {
        this.tasksNumber = tasksNumber;
    }

    public ArrayList getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public String getFilePath(){
        return filePath;
    }

    public void saveData() throws FileNotFoundException {

        System.out.println("Saving data...");
        PrintWriter writer = new PrintWriter(filePath);
        writer.print("");
        writer.close();
        tasksNumber = 0;
        for(Task task : tasks) {
            actions.saveTask(task, this);
        }

    }

}
