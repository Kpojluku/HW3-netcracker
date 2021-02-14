package com.goltsov.model;

import com.goltsov.exeptions.FileBadFormatException;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.UUID;

public class UserLog {
    ArrayList<String> list = new ArrayList<>();


    public void read(Greeting greeting) {
        list.add("Имя:" + greeting.getFirstName());
        list.add("Фамилия:" + greeting.getLastName());
        list.add("Отчество:" + greeting.getPatronymic());
        list.add("Уровень зарплаты:" + greeting.getSalary());
        list.add("email:" + greeting.getEmail());
        list.add("Место работы:" + greeting.getPlaceOfWork());
    }

    public void write() {
        try (
                FileOutputStream fileOutputStream = new FileOutputStream("src\\resources\\user.txt", true);
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fileOutputStream))
        ) {
            for (String s : list) {
                writer.write(s + "#");
            }
            writer.write("\n\r");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String uploadFile(MultipartFile file) {
        if (file != null) {
            File fileDir = new File(System.getProperty("user.dir") + "\\" + "src\\resources");
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file.getOriginalFilename();
            try {
                File newFile = new File(fileDir + "\\" + resultFileName);
                file.transferTo(newFile);
                readFromFile(newFile);
            } catch (Exception e) {
                e.printStackTrace();
                return "error";
            }
            return "success";
        }
        return "error";
    }

    private void readFromFile(File uploadFile) {
        try (
                FileWriter fileWriter = new FileWriter("src\\resources\\user.txt", true);
                BufferedWriter writer = new BufferedWriter(fileWriter);
                FileReader fileReader = new FileReader(uploadFile);
                BufferedReader reader = new BufferedReader(fileReader)
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                checkFormat(line);
                writer.write("\n\r");
                writer.write(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkFormat(String line) throws FileBadFormatException {
        if (!line.isEmpty()) {
            if (!(line.endsWith("#")) ||
                    !line.contains("Имя:") ||
                    !line.contains("#Фамилия:") ||
                    !line.contains("#Отчество:") ||
                    !line.contains("#email") ||
                    !line.contains("#Место работы:")) {
                throw new FileBadFormatException("Неверный формат записи данных в файле");
            }
        }
    }

    public String findUser(Greeting greeting){
        try(
        FileReader fileReader = new FileReader("src\\resources\\user.txt");
        BufferedReader reader = new BufferedReader(fileReader)
        ) {
            while (reader.ready()){
                list.add(reader.readLine());
            }
            String firstName = greeting.getFirstName();
            String lastName = greeting.getLastName();

            for(String s: list){
                if(s.contains(firstName) && s.contains(lastName)){
                    parseLine(s, greeting);
                    break;
                }
            }
            return "result2";
        }catch (IOException e){
            e.printStackTrace();
        }
        return "error";
    }

    private void parseLine(String line, Greeting greeting){
        String[] split = line.split("#");
        greeting.setPatronymic(split[2].substring(split[2].indexOf(":")+1));
        greeting.setSalary(split[3].substring(split[3].indexOf(":")+1));
        greeting.setEmail(split[4].substring(split[4].indexOf(":")+1));
        greeting.setPlaceOfWork(split[5].substring(split[5].indexOf(":")+1));
    }

}
