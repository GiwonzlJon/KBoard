package com.lec.spring.controller5;

import com.lec.spring.domain.Post;
import com.lec.spring.domain.PostValidator;
import com.lec.spring.service.BoardService;
import com.lec.spring.util.U;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

// Controller layer
//  request 처리 ~ response


@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardService boardService;


    public BoardController() {
        System.out.println("BoardController() 생성");
    }

    @GetMapping("/write")
    public void write() {
    }

    @PostMapping("/write")
    public String writeOk(
            @RequestParam Map<String , MultipartFile> files     //첨부파일

            , @Valid Post post
            , BindingResult result
            , Model model   // 매개변수 선언시 BindingResult 보다 Model 을 뒤에 두어야 한다.
            , RedirectAttributes redirectAttrs
    ) {
        // validation 에러가 있었다면 redirect 할거다!
        if (result.hasErrors()) {

            // addAttribute
            //    request parameters로 값을 전달.  redirect URL에 query string 으로 값이 담김
            //    request.getParameter에서 해당 값에 액세스 가능
            // addFlashAttribute
            //    일회성. 한번 사용하면 Redirect후 값이 소멸
            //    request parameters로 값을 전달하지않음
            //    '객체'로 값을 그대로 전달
//            redirectAttrs.addFlashAttribute("user", post.getUser());
            redirectAttrs.addFlashAttribute("subject", post.getSubject());
            redirectAttrs.addFlashAttribute("content", post.getContent());

            for (var err : result.getFieldErrors()) {
                redirectAttrs.addFlashAttribute("error_" + err.getField(), err.getCode());
            }

            return "redirect:/board/write";
        }

        model.addAttribute("result", boardService.write(post, files));
        return "board/writeOk";
    }


    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("post", boardService.detail(id));
        return "board/detail";

    }

    @GetMapping("/list")
    public void list(Integer page, Model model) {
//        List<Post> list = boardService.list();
//        model.addAttribute("list", list);
        boardService.list(page, model);

    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable Long id, Model model) {
        Post post = boardService.selectById(id);
        model.addAttribute("post", post);
        return "board/update";
    }

    @PostMapping("/update")
    public String updateOk(
            @RequestParam Map<String , MultipartFile> files  //  새로 추가될 첨부파일들
            , Long [] delfile  //  삭제 될 파일들
            , @Valid Post post
            , BindingResult result
            , Model model
            , RedirectAttributes redirectAttrs
    ) {
        if (result.hasErrors()) {

            redirectAttrs.addFlashAttribute("user", post.getUser());
            redirectAttrs.addFlashAttribute("subject", post.getSubject());
            redirectAttrs.addFlashAttribute("content", post.getContent());

            for (var err : result.getFieldErrors()) {
                redirectAttrs.addFlashAttribute("error_" + err.getField(), err.getCode());
            }

            return "redirect:/board/update/" + post.getId();
        }


        model.addAttribute("result", boardService.update(post, files, delfile));
        return "board/updateOk";
    }

    @PostMapping("/delete")
    public String deleteOk(Long id, Model model) {
        int result = boardService.deleteById(id);
        model.addAttribute("result", result);
        return "board/deleteOk";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        System.out.println("initBinder() 호추르");
        binder.setValidator(new PostValidator());
    }

    // 페이징
    // pageRows 변경시 동작
    @PostMapping("/pageRows")
    public String pageRows(Integer page, Integer pageRows){
        U.getSession().setAttribute("pageRows", pageRows);
        return "redirect:/board/list?page=" + page;
    }



}//  end Controller
