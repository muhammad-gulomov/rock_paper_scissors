package uz.muhammadtrying.rock_paper_scissors.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.muhammadtrying.rock_paper_scissors.entity.Game;
@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {
  }