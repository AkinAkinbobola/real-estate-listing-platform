package com.akinbobola.backend.file;

import com.akinbobola.backend.floorPlan.FloorPlanRepository;
import com.akinbobola.backend.listingImage.ListingImageRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class FileReaderService {

    private final ListingImageRepository listingImageRepository;
    private final FloorPlanRepository floorPlanRepository;

    public byte[] readFile (String imageUrl) throws IOException {
        if (imageUrl == null) {
            return null;
        }

        Path path = Paths.get(imageUrl);

        if (!Files.exists(path)) {
            throw new IOException("File does not exist");
        }

        return Files.readAllBytes(path);
    }

    public Path getImagePath (Integer imageId) {
        return listingImageRepository.findById(imageId)
                .map(image -> Paths.get(image.getImageUrl()))
                .orElseThrow(() -> new EntityNotFoundException("No image found with id: " + imageId));
    }

    public Path getFloorPath (Integer floorPlanId) {
        return floorPlanRepository.findById(floorPlanId)
                .map(floorPlan -> Paths.get(floorPlan.getPlanUrl()))
                .orElseThrow(() -> new EntityNotFoundException("No floor plan found with id: " + floorPlanId));
    }
}
