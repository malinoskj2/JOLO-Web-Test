package server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import server.model.Authentication;
import server.model.Examiner;
import server.repository.ExaminerRepository;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private ExaminerRepository repo;

    @RequestMapping(value = "/login",
            method = RequestMethod.POST,
            produces = "text/plain")
    public String login(@RequestBody Authentication credentials) {
        return String.format("Received %nLogin:%s %nPassword:%s",
                credentials.getUsername(),
                credentials.getPassword());
    }

    @RequestMapping(value = "/register",
            method = RequestMethod.POST,
            produces = "text/plain")
    public String login(@RequestBody Examiner examiner) {
        this.repo.save(examiner);

        return String.format("Received %nLogin:%s %nPassword:%s",
                examiner.getfName(),
                examiner.getlName(),
                examiner.getExamID());
    }
}
