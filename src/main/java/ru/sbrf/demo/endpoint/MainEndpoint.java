package ru.sbrf.demo.endpoint;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.sbrf.demo.dto.Response;
import ru.sbrf.demo.service.UrlGettingService;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MainEndpoint {

    private final UrlGettingService urlGettingService;

    /**
     * Получение содержимого удаленного ресурса
     * @param url ссылка на удаленный ресурс
     * @return html-страница с информацией о url и его содержимом
     */
    @GetMapping
    public String getResource(@RequestParam String url, Model model) {
        Optional<Response> optionalResponse = urlGettingService.getContentByUrl(url);
        model.addAttribute("url", url);
        if (optionalResponse.isPresent()) {
            model.addAttribute("date", optionalResponse.get().getDateHeader());
            model.addAttribute("status", optionalResponse.get().getStatus());
            model.addAttribute("content", optionalResponse.get().getContent());
        } else {
            model.addAttribute("content", "Couldn't get remote resource content");
        }
        return "index";
    }

}
