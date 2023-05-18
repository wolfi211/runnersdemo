package hu.gde.runnersdemo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RunnerServiceTest {

    private RunnerService runnerService;
    @Mock
    private RunnerRepository runnerRepository;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        runnerService = new RunnerService(runnerRepository);
    }

    @Test
    void getAverageLaptime() {
        RunnerEntity runner = new RunnerEntity();
        LapTimeEntity lapTime1 = new LapTimeEntity();
        lapTime1.setTimeSeconds(20);

        LapTimeEntity lapTime2 = new LapTimeEntity();
        lapTime2.setTimeSeconds(30);

        runner.getLaptimes().add(lapTime1);
        runner.getLaptimes().add(lapTime2);

        when(runnerRepository.findById(runner.getRunnerId())).thenReturn(Optional.of(runner));

        double averageLaptimeResult = runnerService.getAverageLaptime(runner.getRunnerId());
        assertEquals(25, averageLaptimeResult);

        verify(runnerRepository).findById(runner.getRunnerId());
    }

    @Test
    void getAverageLaptimeNotNull() {
        RunnerEntity runner = new RunnerEntity();

        when(runnerRepository.findById(runner.getRunnerId())).thenReturn(Optional.empty());

        double averageLaptimeResult = runnerService.getAverageLaptime(runner.getRunnerId());
        assertEquals(25, averageLaptimeResult);

        verify(runnerRepository).findById(runner.getRunnerId());
    }
}