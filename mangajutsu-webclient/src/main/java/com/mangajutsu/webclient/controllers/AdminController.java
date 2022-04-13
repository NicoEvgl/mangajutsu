package com.mangajutsu.webclient.controllers;

import java.util.ArrayList;
import java.util.List;

import com.mangajutsu.webclient.models.RoleModel;
import com.mangajutsu.webclient.models.UserModel;
import com.mangajutsu.webclient.proxies.MangajutsuProxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import feign.FeignException;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private MangajutsuProxy mangajutsuProxy;

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/user-list")
    private String userList(final Model model) {
        List<UserModel> users = new ArrayList<>();
        users = mangajutsuProxy.getUserList();
        model.addAttribute("users", users);
        return "admin/user_list";
    }

    @GetMapping("{id}/add-role")
    private String assignRole(@PathVariable Integer id, final Model model) {
        List<RoleModel> roles = mangajutsuProxy.getRoles();
        model.addAttribute("roles", roles);
        model.addAttribute("role", new RoleModel());
        return "admin/assign_role";
    }

    @PostMapping("{id}/add-role")
    private String assignRole(@PathVariable Integer id, @ModelAttribute("role") RoleModel role,
            RedirectAttributes redirectAttributes, final Model model) {
        role = mangajutsuProxy.getRoleDetails(role.getNameRole());
        try {
            mangajutsuProxy.addRoleToUser(role, id);
        } catch (FeignException e) {
            model.addAttribute("error",
                    messageSource.getMessage("error.assign-role", null, LocaleContextHolder.getLocale()));
            model.addAttribute("role", role);
            return "admin/assign_role";
        }
        redirectAttributes.addFlashAttribute("success",
                messageSource.getMessage("assign-role.success.msg", null, LocaleContextHolder.getLocale()));

        model.addAttribute("id", id);
        model.addAttribute("role", role);

        return "redirect:/admin/user-list";
    }

    @GetMapping("/account-locked/{id}")
    private String accountLocked(@PathVariable Integer id, RedirectAttributes redirectAttributes, final Model model) {
        try {
            mangajutsuProxy.accountLocked(id);
        } catch (FeignException e) {
            model.addAttribute("error",
                    messageSource.getMessage("error.admin.lock-account", null, LocaleContextHolder.getLocale()));
            model.addAttribute("user", mangajutsuProxy.getUserDetails(id));
            return "admin/user_list";
        }
        redirectAttributes.addFlashAttribute("success",
                messageSource.getMessage("account-locked.success.msg", null, LocaleContextHolder.getLocale()));
        model.addAttribute("user", mangajutsuProxy.getUserDetails(id));

        return "redirect:/admin/user-list";
    }

    @GetMapping("/account-non-locked/{id}")
    private String accountNonLocked(@PathVariable Integer id, RedirectAttributes redirectAttributes,
            final Model model) {
        try {
            mangajutsuProxy.accountNonLocked(id);
        } catch (FeignException e) {
            model.addAttribute("error",
                    messageSource.getMessage("error.admin.non-lock-account", null, LocaleContextHolder.getLocale()));
            model.addAttribute("user", mangajutsuProxy.getUserDetails(id));
            return "admin/user_list";
        }
        redirectAttributes.addFlashAttribute("success",
                messageSource.getMessage("account-non-locked.success.msg", null, LocaleContextHolder.getLocale()));
        model.addAttribute("user", mangajutsuProxy.getUserDetails(id));

        return "redirect:/admin/user-list";
    }
}
