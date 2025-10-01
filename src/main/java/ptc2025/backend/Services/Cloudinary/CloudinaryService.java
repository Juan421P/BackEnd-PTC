package ptc2025.backend.Services.Cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

@Service
public class CloudinaryService {

    private static final long MAX_FILE_SIZE = 20 * 1024 * 1024;
    private static final long MAX_DOC_SIZE = 2000 * 1024 * 1024;

    private static final String[] ALLOWED_EXTENSIONS = {".jpg",".png",".jpeg"};
    private static final String[] ALLOWED_DOCS = {".pdf", ".doc", ".docx"};

    private final Cloudinary cloudinary;

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String uploadImage(MultipartFile file)throws IOException{
        validateImage(file);
        Map<?, ?> uploadResult = cloudinary.uploader()
                .upload(file.getBytes(), ObjectUtils.asMap(
                        "resource_type", "auto",
                        "quality", "auto:good"
                ));
        return (String) uploadResult.get("secure_url");
    }

    public String UploadImage(MultipartFile file, String folder) throws IOException {
        validateImage(file);
        String originalFileName = file.getOriginalFilename();
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf(".")).toLowerCase();
        String uniqueFilename = "img_" + UUID.randomUUID() + fileExtension;

        Map<String, Object> options = ObjectUtils.asMap(
                "folder", folder,
                "public_id", uniqueFilename,
                "use_filename", false,
                "unique_filename", false,
                "overwrite", false,
                "resource_type", "auto",
                "quality", "auto:good"
        );
        Map<?,?> uploadResult = cloudinary.uploader().upload(file.getBytes(), options);
        return (String) uploadResult.get("secure_url");
    }

    private void validateImage(MultipartFile file) {
        if(file.isEmpty()) throw new IllegalArgumentException("El archivo no puede estar vacío");
        if(file.getSize() > MAX_FILE_SIZE) throw new IllegalArgumentException("El tamaño del archivo no puede exceder los 20MB");
        String originalFileName = file.getOriginalFilename();
        if(originalFileName == null) throw new IllegalArgumentException("Nombre de archivo no valido");
        String extension = originalFileName.substring(originalFileName.lastIndexOf(".")).toLowerCase();
        if(!Arrays.asList(ALLOWED_EXTENSIONS).contains(extension)) throw new IllegalArgumentException("Solo se permiten archivos .jpg, .jpeg, y .png");
        if(!file.getContentType().startsWith("image/")) throw new IllegalArgumentException("El archivo debe ser una imagen valida");
    }

    public String uploadDocument(MultipartFile file)throws IOException{
        validateDocument(file);
        Map<?, ?> uploadResult = cloudinary.uploader()
                .upload(file.getBytes(), ObjectUtils.asMap(
                        "resource_type", "raw"
                ));
        return (String) uploadResult.get("secure_url");
    }

    public String UploadDocument(MultipartFile file, String folder)throws IOException{
        validateDocument(file);
        String originalFileName = file.getOriginalFilename();
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf(".")).toLowerCase();
        String uniqueFileName = "doc_" + UUID.randomUUID() + fileExtension;

        Map<String, Object> options = ObjectUtils.asMap(
                "folder", folder,
                "public_id", uniqueFileName,
                "use_filename", false,
                "unique_filename", false,
                "overwrite", false,
                "resource_type", "raw"
        );
        Map<?,?> uploadResult = cloudinary.uploader().upload(file.getBytes(),options);
        return (String) uploadResult.get("secure_url");
    }

    private void validateDocument(MultipartFile file) {
        if(file.isEmpty()) throw new IllegalArgumentException("El documento no puede estar vacío");
        if(file.getSize() > MAX_DOC_SIZE) throw new IllegalArgumentException("el tamaño del archivo no puede exceder los ");
        String originalFileName = file.getOriginalFilename();
        if(originalFileName == null) throw new IllegalArgumentException("Nombre de documento no valido");
        String extension = originalFileName.substring(originalFileName.lastIndexOf(".")).toLowerCase();
        if(!Arrays.asList(ALLOWED_DOCS).contains(extension)) throw new IllegalArgumentException("Solo se permiten documentos .pdf, .doc, .docx");
        if(!file.getContentType().startsWith("document/")) throw new IllegalArgumentException("El archivo debe ser un documento valido");
    }
}
