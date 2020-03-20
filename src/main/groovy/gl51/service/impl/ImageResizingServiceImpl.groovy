package gl51.service.impl

import gl51.data.Image
import gl51.service.CloudUploadService
import gl51.service.ImageFiligraneService
import gl51.service.ImageResizingService
import gl51.service.ImageService

import gl51.service.UptdateDataBaseService

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageResizingServiceImpl implements ImageResizingService {
    @Inject
    ImageService imageService

    @Inject
    CloudUploadService cloudUploadService

    @Inject
    UptdateDataBaseService updateDataBaseService

    @Inject
    ImageFiligraneService imageFiligraneService

    @Override
    Image resize(Image image_source, int new_dimension_x, int new_dimension_y) {
    }

    @Override
    Image getAndResizeImage() {
        //Récupération de l'image
        Image image_source=imageService.fetchImage()

        //Redimensionnement de l'image au format 1024*1024
        Image new_image=resize(image_source,1024,1024)

        //Création du thumbail
        Image thumbnail=resize(image_source,50,50)

        //Ajout du filigrane
        Image logo=imageFiligraneService.add_filigrane(thumbnail)

        //Stockage dans le cloud
        cloudUploadService.uploadToCloud(new_image)
        cloudUploadService.uploadToCloud(logo)

        //Mise à jour de la base de données
        updateDataBaseService.updateDatabase(new_image.getDimension_x(),new_image.getDimension_y(),new_image.getNom())

    }
}
