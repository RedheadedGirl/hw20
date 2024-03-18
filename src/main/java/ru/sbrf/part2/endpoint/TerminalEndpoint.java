package ru.sbrf.part2.endpoint;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sbrf.part2.exceptions.NotEnoughMoneyException;
import ru.sbrf.part2.service.TerminalServer;

@RestController
@RequiredArgsConstructor
@RequestMapping("/terminal")
public class TerminalEndpoint {

    private final TerminalServer terminalServer;

    /**
     * Получить актуальный баланс
     * @return баланс
     */
    @GetMapping("/balance")
    public ResponseEntity checkBalance() { //  curl.exe http://localhost:8080/terminal/balance
        return ResponseEntity.ok(terminalServer.getBalance());
    }

    /**
     * Пополнить счет на указанную сумму
     * @param sum сумма
     * @return статус действия
     */
    @PostMapping("/add")
    public ResponseEntity addMoney(@RequestParam Integer sum) { // curl.exe -X POST -F 'sum=100' http://localhost:8080/terminal/add
        terminalServer.addMoney(sum);
        return ResponseEntity.ok().build();
    }

    /**
     * Снять указанную сумму со счета
     * @param sum сумма
     * @return статус действия
     */
    @PostMapping("/take")
    public ResponseEntity takeMoney(@RequestParam Integer sum) { // curl.exe -X POST -F 'sum=100' http://localhost:8080/terminal/take
        try {
            terminalServer.takeMoney(sum);
        } catch (NotEnoughMoneyException exception) {
            return ResponseEntity.internalServerError().body("Введенная Вами сумма не может быть снята со счета");
        }
        return ResponseEntity.ok().build();
    }

}
