package Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/serveys")
public class SurveyController {

    private List<String> surveys = new ArrayList<>();

    @GetMapping
    public ResponseEntity<List<String>> getAllSurveys() {
        return new ResponseEntity<>(surveys, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> addSurvey(@RequestBody String survey) {
        return new ResponseEntity<>("Survey added successfully", HttpStatus.CREATED);
    }

    @DeleteMapping ("/{index}")
    public ResponseEntity<String> deleteSurvey(@PathVariable int index) {
        try {
            surveys.remove(index);
            return new ResponseEntity<>("Survey deleted successfully", HttpStatus.OK);
        }  catch (IndexOutOfBoundsException e) {
            return new ResponseEntity<>("Index out of bounds", HttpStatus.BAD_REQUEST);
        }
    }


}
