package shift.labTask.Controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shift.labTask.Repository.IntervalRepository;
import shift.labTask.IntervalClass.ourInterval;

import java.util.*;

@RestController
@RequestMapping("/api/v1/intervals")
public class IntervalController {
    @Autowired
    private IntervalRepository repository;

    @PostMapping("/saveInterval")
    public String saveInterval(@RequestBody ourInterval interval){
        repository.save(interval);

        return "Interval saved.";
    }

    @GetMapping("/min")
    public ourInterval getMinInterval(@RequestParam("kind") String kind){

        if(kind.equals("digits") || kind.equals("letters")) {
            return repository.findMinInterval();
        }

        return null;
    }

    @GetMapping("/getAllIntervals")
    public List<ourInterval> getAllIntervals(){
        return repository.findAll();
    }

    @PostMapping(value = "/merge")
    public ResponseEntity<Void> addIntervals(@RequestParam("kind") String kind, @RequestBody List<ourInterval> intervals) {

        if (kind.equals("digits") || kind.equals("letters")) {
            List<ourInterval> mergedIntervals = mergeIntervals(intervals);

            if (!mergedIntervals.isEmpty()) {
                repository.saveAll(mergedIntervals);
            }
        }
        return ResponseEntity.ok().build();
    }

    private String compareLetters(String first, String second){
        if (first.charAt(0) > second.charAt(0)){
            return first;
        }
        else{
            return second;
        }
    }
    private List<ourInterval> mergeIntervals(List<ourInterval> intervals){
        if (intervals.isEmpty()){
            return null;
        }

        List<ourInterval> mergedIntervals = new ArrayList<>();

        Collections.sort(intervals, Comparator.comparing(ourInterval::getFirstInterval));

        ourInterval current = intervals.get(0);

        for (int i = 1; i<intervals.size(); i++){
            ourInterval next = intervals.get(i);

            if (current.getLastInterval().charAt(0) >= next.getFirstInterval().charAt(0)){
                current.setLastInterval(compareLetters(current.getLastInterval(), next.getLastInterval()));
            }
            else{
                mergedIntervals.add(current);
                current = next;
            }
        }

        mergedIntervals.add(current);

        return mergedIntervals;
    }
}
