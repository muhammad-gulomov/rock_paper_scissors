package uz.muhammadtrying.rock_paper_scissors.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.muhammadtrying.rock_paper_scissors.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
}