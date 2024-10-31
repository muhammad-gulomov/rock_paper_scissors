package uz.muhammadtrying.rock_paper_scissors.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.muhammadtrying.rock_paper_scissors.entity.GameStat;

import java.util.List;

@Repository
public interface GameStatRepository extends JpaRepository<GameStat, Integer> {
    List<GameStat> findAllByGameIdOrderByScoreDescDuration(Integer gameId);
}