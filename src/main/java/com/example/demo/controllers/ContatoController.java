package com.example.demo.controllers;

import com.example.demo.models.Contato;
import com.example.demo.repositories.ContatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.transaction.Transactional;
import java.util.List;


@Controller
@Transactional
public class ContatoController {


    @Autowired
    private ContatoRepository contatoRepository;


    @GetMapping("/")

    public String contatoForm() {

        return "cadastro";
    }

    public List<Contato> verificaContatos() {

        return contatoRepository.findAll();
    }

    @ModelAttribute(value = "contato")
    public Contato novoContato()
    {
        return new Contato();
    }


    @GetMapping("/listagem")

    public String listaContatos(Model model) {

        model.addAttribute("listaContatos", contatoRepository.findAll());

        return "listagem";
    }

    @PostMapping("/cadastro")
    public String cadastro(Contato contato) {

        List<Contato> contatos = verificaContatos();

        if (contatos.isEmpty()) {

            contatoRepository.save(contato);
        } else {

            for (int i = 0; i < contatos.size(); i++) {

                if (contato.getNome().equalsIgnoreCase(contatos.get(i).getNome())) {

                    return "erroProcessamento";
                }
            }
            contatoRepository.save(contato);

        }
        return "redirect:/";
    }

}
