package com.example.springbootonetwomanyrestapibidirectional.controller;


import com.example.springbootonetwomanyrestapibidirectional.jpa.Library;
import com.example.springbootonetwomanyrestapibidirectional.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(path = "/libraries")

public class LibraryController {

    @Autowired
    private LibraryService libraryService;

    @InitBinder
    public void InitBinder(WebDataBinder data)
    {
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        data.registerCustomEditor(Date.class, new CustomDateEditor (s, true));
    }

    @RequestMapping("")
    public String getLibraries(Model model)
    {
        Library library = new Library();

        return findPaginated(1,model,library);
    }
    @RequestMapping(path = "/saveLibrary",method = RequestMethod.POST)
    public String saveLibrary(@ModelAttribute("libraryNew")@Valid Library library, BindingResult result, Model model)
    {
        if (result.hasErrors())
        {
            return findPaginated(1,model,library);
        }
        boolean checkLibraryName = libraryService.checkLibraryName(library.getName());
        if (checkLibraryName==false)
        {
            return "redirect:/library?errorlibraryname=LibraryName is existed";
        }

        boolean bl = libraryService.saveLibrary(library);
        if (bl)
        {
            return "redirect:/library?success=Add New Library success";
        }
        return "redirect:/library?error=Add New Library error";

    }

    @RequestMapping(path = "/editLibrary")
    public String editLibrary(@RequestParam("id")Integer id, Model model)
    {
        Library library = libraryService.getLibraryById(id);
        model.addAttribute("libraryEdit",library);
        return "library/libraryEdit";
    }

    @RequestMapping(path = "/updateLibrary",method = RequestMethod.POST)
    public String updateLibrary(@ModelAttribute("libraryEdit")Library library)
    {
        boolean checkLibraryName = checkLibraryNameEdit(library.getName(),library.getId());
        if (checkLibraryName==false)
        {
            return "redirect:/library/editLibrary?id="+library.getId()+"&&errorlibraryname=Library Name is existed";
        }
        boolean bl = libraryService.updateLibrary(library);
        if (bl)
        {
            return "redirect:/library?success=Update Library success";
        }
        return "redirect:/library?error=Update Library error";

    }

    @RequestMapping(path = "/deleteLibrary")
    public String deleteLibrary(@RequestParam("id")Integer id)
    {
        boolean bl = libraryService.deleteLibrary(id);
        if (bl)
        {
            return "redirect:/library?success=Delete Library success";
        }
        return "redirect:/library?error=Delete Library error";
    }

    @RequestMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo")int pageNo, Model model, Library library)
    {
        int pageSize = 10;
        Page<Library> page = libraryService.findPaginated(pageNo,pageSize);
        List<Library> libraries = page.getContent();
        model.addAttribute("currentPage",pageNo);
        model.addAttribute("totalPages",page.getTotalPages());
        model.addAttribute("totalItems",page.getTotalElements());
        model.addAttribute("listLibraries",libraries);
        model.addAttribute("libraryNew",library);
        return "library/libraryList";
    }

    public boolean checkLibraryNameEdit(String name, int id)
    {
        Library library = libraryService.getLibraryById(id);
        boolean checkLibraryName = libraryService.checkLibraryName(name);
        if (checkLibraryName==false)
        {
            if (name.equals(library.getName()))
            {
                return true;
            }
            else {
                return false;
            }
        }
        return true;
    }
}
