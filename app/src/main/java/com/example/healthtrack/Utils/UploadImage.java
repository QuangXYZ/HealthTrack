package com.example.healthtrack.Utils;

public class UploadImage {


//    private void UploadImage() {
//        if (filePath != null) {
//            progressBar.setVisibility(View.VISIBLE);
//            final StorageReference childRef = storageRef.child("product_images").child(System.currentTimeMillis() + ".jpg");
//            final UploadTask uploadTask = childRef.putFile(filePath);
//            uploadTask.addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    String message = e.toString();
//                    Toast.makeText(UpdateProductActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
//                    progressBar.setVisibility(View.GONE);
//                }
//            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    Toast.makeText(UpdateProductActivity.this, "Đã tải ảnh...", Toast.LENGTH_SHORT).show();
//                    Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
//                        @Override
//                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
//                            if (!task.isSuccessful()) {
//                                throw task.getException();
//                            }
//                            downloadImageUrl = childRef.getDownloadUrl().toString();
//                            return childRef.getDownloadUrl();
//                        }
//                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Uri> task) {
//                            if (task.isSuccessful()) {
//                                downloadImageUrl = task.getResult().toString();
//                                Log.d("imagUrl", downloadImageUrl);
//                                product.setPhotoUrl(downloadImageUrl);
//                                updateDataProduct();
//                            }
//                        }
//                    });
//                }
//            });
//
//        } else {
//            Toast.makeText(UpdateProductActivity.this, "Chọn ảnh mới", Toast.LENGTH_SHORT).show();
//        }
//    }
}
