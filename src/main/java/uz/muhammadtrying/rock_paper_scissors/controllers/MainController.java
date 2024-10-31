package uz.muhammadtrying.rock_paper_scissors.controllers;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uz.muhammadtrying.rock_paper_scissors.entity.GameStat;
import uz.muhammadtrying.rock_paper_scissors.entity.User;
import uz.muhammadtrying.rock_paper_scissors.service.MainService;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class MainController {

    private final MainService mainService;
    private final HttpSession httpSession;

    @GetMapping
    public String index(Model model) {
        if (httpSession.getAttribute("readyToBeDeleted") != null) {
            User user = (User) httpSession.getAttribute("user");
            mainService.deleteUserById(user.getId());
            httpSession.removeAttribute("nickname");
            httpSession.removeAttribute("readyToBeDeleted");
            Integer gameId = (Integer) httpSession.getAttribute("gameId");
            mainService.deleteGameById(gameId);
            httpSession.removeAttribute("gameId");
        }
        model.addAttribute("games", mainService.fetchAllGame());
        return "index";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("games", mainService.fetchAllGame());
        return "admin";
    }

    @PostMapping(value = "/create-game", consumes = "application/x-www-form-urlencoded")
    public String createGame(@RequestParam("gameName") String gameName, RedirectAttributes redirectAttributes) {
        mainService.saveGame(gameName);
        redirectAttributes.addFlashAttribute("message", "Game created successfully!");
        return "redirect:/admin";
    }

    @PostMapping("/start-game")
    public String startGame(@RequestParam Integer id) {
        mainService.startGame(id);
        return "redirect:/admin";
    }

    @GetMapping("/enter-nickname/{id}")
    public String enterNickname(@PathVariable Integer id) {
        httpSession.setAttribute("gameId", id);
        return "enter-name";
    }

    @GetMapping("/join-game")
    public String startGame(@RequestParam String nickname, Model model, HttpSession httpSession) {
        if (httpSession.getAttribute("nickname") == null) {
            User user = mainService.saveUser(nickname);
            httpSession.setAttribute("user", user);
            httpSession.setAttribute("nickname", nickname);
        }
        model.addAttribute("players", mainService.findAllUsersInGame());
        model.addAttribute("game", mainService.findGameById((Integer) httpSession.getAttribute("gameId")));
        return "game";
    }

    @GetMapping("/start-game")
    public String startGame() {
        return "playground";
    }

    @PostMapping("/save-stats")
    @ResponseBody
    public ResponseEntity<Map<String, String>> saveGameResult(@RequestBody GameStat gameStat) {
        gameStat.setGameId((Integer) httpSession.getAttribute("gameId"));
        gameStat.setNickname(((User) httpSession.getAttribute("user")).getNickname());
        mainService.saveGameResult(gameStat);
        Map<String, String> response = new HashMap<>();
        response.put("redirect", "/results");
        return ResponseEntity.ok(response);
    }


    @GetMapping("/results")
    public String result(Model model) {
        model.addAttribute("gameStats", mainService.fetchAllByGameIdOrderByScores((Integer) httpSession.getAttribute("gameId")));
        httpSession.setAttribute("readyToBeDeleted", true);
        return "results";
    }

    @PostMapping("/delete-game")
    public String deleteGame(@RequestParam Integer id) {
        mainService.deleteGameById(id);
        return "redirect:/admin";
    }
}
