//package telran.goods.exception;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.context.request.WebRequest;
//
//
//@ControllerAdvice
//public class GlobalExceptionHandler {
//
//    @ExceptionHandler(FarmerNotFoundException.class)
//    public ResponseEntity<?> handleFarmerNotFoundException(FarmerNotFoundException ex, WebRequest request) {
//        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(ProductNotFoundException.class)
//    public ResponseEntity<?> handleProductNotFoundException(ProductNotFoundException ex, WebRequest request) {
//        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<?> handleGlobalException(Exception ex, WebRequest request) {
//        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//}