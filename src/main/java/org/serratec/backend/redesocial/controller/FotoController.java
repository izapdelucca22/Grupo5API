package org.serratec.backend.redesocial.controller;

import java.io.IOException;

import org.serratec.backend.redesocial.model.Foto;
import org.serratec.backend.redesocial.service.FotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/foto")
public class FotoController {

	@Autowired
	private FotoService fotoService;
	
	@PostMapping(value = "/{id}",consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public Foto inserir(@RequestPart MultipartFile file, @PathVariable Long id) throws IOException {
		return fotoService.inserir(file, id);
	}
}

