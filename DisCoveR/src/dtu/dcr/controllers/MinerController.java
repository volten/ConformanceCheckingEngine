package dtu.dcr.controllers;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.deckfour.xes.model.XLog;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import dk.ku.di.dcrgraphs.BitDCRGraph;
import dk.ku.di.discover.algorithms.bitparnek.BitParNek;
import dk.ku.di.discover.algorithms.bitparnek.BitParNekLogAbstractions;
import dtu.processmining.XLogHelper;

@RestController
@RequestMapping("/api/v1/discover/")
@CrossOrigin
public class MinerController {

	@PostMapping("/mine")
	public ResponseEntity<String> mine(@RequestParam("file") MultipartFile file) {
		File tmpFile = null;
		try {
			String extension = "";
			if (file.getOriginalFilename().contains("xes.gz")) {
				extension = ".xes.gz";
			} else if (file.getOriginalFilename().contains("xes")) {
				extension = ".xes";
			}
			tmpFile = File.createTempFile("log", extension);
			FileUtils.copyInputStreamToFile(file.getInputStream(), tmpFile);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			XLog log = XLogHelper.readFile(tmpFile.getAbsolutePath());
			BitParNek disco = new BitParNek();
			BitParNekLogAbstractions helper = disco.helper(log);
			BitDCRGraph g = disco.mine(helper); //
			disco.findAdditionalConditions(helper, g);
			disco.clean(helper, g);
			return ResponseEntity.ok(g.toDCRLanguage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
}
