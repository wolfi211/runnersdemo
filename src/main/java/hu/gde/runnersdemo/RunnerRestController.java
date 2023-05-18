package hu.gde.runnersdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/runner")
public class RunnerRestController {

    @Autowired
    private LapTimeRepository lapTimeRepository;
    private RunnerRepository runnerRepository;
    private ShoeRepository shoeRepository;

    @Autowired
    public RunnerRestController(
            RunnerRepository runnerRepository,
            LapTimeRepository lapTimeRepository,
            ShoeRepository shoeRepository)
    {
        this.runnerRepository = runnerRepository;
        this.lapTimeRepository = lapTimeRepository;
        this.shoeRepository = shoeRepository;
    }

    @GetMapping("/{id}")
    public RunnerEntity getRunner(@PathVariable Long id) {
        return runnerRepository.findById(id).orElse(null);
    }

    @GetMapping("/{id}/averagelaptime")
    public double getAverageLaptime(@PathVariable Long id) {
        RunnerEntity runner = runnerRepository.findById(id).orElse(null);
        if (runner != null) {
            List<LapTimeEntity> laptimes = runner.getLaptimes();
            int totalTime = 0;
            for (LapTimeEntity laptime : laptimes) {
                totalTime += laptime.getTimeSeconds();
            }
            return (double) totalTime / laptimes.size();
        } else {
            return -1.0;
        }
    }

    @GetMapping("/averageage")
    public double getAverageAge() {
        List<RunnerEntity> runners = runnerRepository.findAll();

        if(runners.size() != 0) {

            long totalAge = 0;
            for(RunnerEntity runner : runners) {
                totalAge += runner.getAge();
            }

            return (double)totalAge / (double)runners.size();
        } else {
            return -1.0;
        }
    }

    @GetMapping("")
    public List<RunnerEntity> getAllRunners() {
        return runnerRepository.findAll();
    }

    @PostMapping("/{id}/addlaptime")
    public ResponseEntity addLaptime(@PathVariable Long id, @RequestBody LapTimeRequest lapTimeRequest) {
        RunnerEntity runner = runnerRepository.findById(id).orElse(null);
        if (runner != null) {
            LapTimeEntity lapTime = new LapTimeEntity();
            lapTime.setTimeSeconds(lapTimeRequest.getLapTimeSeconds());
            lapTime.setLapNumber(runner.getLaptimes().size() + 1);
            lapTime.setRunner(runner);
            lapTimeRepository.save(lapTime);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Runner with ID " + id + " not found");
        }
    }

    @PostMapping("/{id}/setshoe")
    public ResponseEntity setShoe(@PathVariable Long id, @RequestBody ShoeRequest shoeRequest) {
        RunnerEntity runner = runnerRepository.findById(id).orElse(null);
        ShoeEntity shoe = shoeRepository.findById(shoeRequest.getShoeId()).orElse(null);
        if (runner != null && shoe != null) {
            runner.setShoe(shoe);
            runnerRepository.save(runner);
            return ResponseEntity.ok().build();
        } else {
          if (runner != null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Runner with ID " + id + " not found");
          } if (shoe != null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Runner with ID " + id + " not found");
          } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong");
          }
        }
    }

    public static class LapTimeRequest {
        private int lapTimeSeconds;

        public int getLapTimeSeconds() {
            return lapTimeSeconds;
        }

        public void setLapTimeSeconds(int lapTimeSeconds) {
            this.lapTimeSeconds = lapTimeSeconds;
        }
    }

    public static class ShoeRequest {
        private long shoeId;

        public long getShoeId() {
            return shoeId;
        }

        public void setShoeId(long shoeId) {
            this.shoeId = shoeId;
        }
    }
}
