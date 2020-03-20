package gl51.service.impl

import gl51.data.Image
import gl51.service.CloudUploadService
import gl51.service.ImageFiligraneService
import gl51.service.ImageResizingService
import gl51.service.ImageService

import gl51.service.UptdateDataBaseService

import javax.inject.Inject

class ImageResizingServiceImpl implements ImageResizingService {
    @Inject
    ImageService image_service

    @Inject
    CloudUploadService cloud_upload_service

    @Inject
    UptdateDataBaseService update_data_base_service

    @Inject
    ImageFiligraneService image_filigrane_service

    @Override
    Image resize(Image image_source, int new_dimension_x, int new_dimension_y) {
    }

    @Override
    Image getAndResizeImage() {
        //Récupération de l'image
        Image image_source=image_service.fetchImage()

        //Redimensionnement de l'image au format 1024*1024
        Image new_image=resize(image_source,1024,1024)

        //Création du thumbail
        Image thumbnail=resize(image_source,50,50)

        //Ajout du filigrane
        Image logo=image_filigrane_service.add_filigrane(thumbnail)

        //Stockage dans le cloud
        cloud_upload_service.uploadToCloud(new_image)
        cloud_upload_service.uploadToCloud(logo)

        //Mise à jour de la base de données
        update_data_base_service.updateDatabase(new_image.getDimension_x(),new_image.getDimension_y(),new_image.getNom())

    }
}
