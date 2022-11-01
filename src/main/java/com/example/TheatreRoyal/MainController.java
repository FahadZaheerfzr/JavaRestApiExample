package com.example.TheatreRoyal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Objects;

@Controller // This means that this class is a Controller
@RequestMapping(path="/authentication") // This means URL's start with /demo (after Application path)
public class MainController {
    DBConnector db;
    MainController(){
        db = new DBConnector();
        db.connect();
    }

    @PostMapping(path="/add") // Map ONLY POST Requests
    public @ResponseBody String addNewUser (@RequestParam String forename
            , @RequestParam String surname, @RequestParam String email, @RequestParam String address,
                                            @RequestParam String password) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        db.runQuery("Insert into Users(forename, surname, email, address, password) Values ('" + forename + "', '" + surname + "', '" + email
                + "', '" + address + "', '" + password + "')");

        return "Saved";
    }

    @PostMapping(path="/login")
    public @ResponseBody String Login(@RequestParam String email, @RequestParam String password) {
        // This returns a JSON or XML with the users
        ResultSet rs = db.runQuery("Select password from Users where email = '" + email + "'");

        ArrayList<String> user_data = db.getQueryResult(rs);

        try {
            String user_password = user_data.get(0);
            if (Objects.equals(user_password, password)) {
                return "Logged in";
            }
            return "Invalid password";
        } catch(IndexOutOfBoundsException e){
            return "Invalid email";
        }

    }
}
