package com.example.controller;
import java.util.stream.Collectors;
import com.example.repository.AssetRepo;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.time.LocalDate;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import java.util.ArrayList;
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.nio.charset.StandardCharsets;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

//import org.apache.commons.csv.CSVFormat;
//import org.apache.commons.csv.CSVParser;
//import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.time.Year;
import com.example.domain.Lot;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import com.example.domain.Lot;
import com.example.service.LocationService;
import com.example.domain.Location;
import com.example.service.AssetService;
import com.example.domain.Asset;
import com.example.domain.Employee;
import com.example.domain.Product;
import com.example.domain.Seller;
import com.example.dto.ProductDTO;
import com.example.service.EmployeeService;
import com.example.service.ProductService;
import com.example.service.SellerService;
import com.example.repository.LotRepo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;
import com.example.domain.Asset;
import java.time.LocalDate;
import java.util.stream.Collectors;
import com.example.service.EmailService;
@Controller
public class AmsController {
	
    @Autowired
    private EmployeeService empService;
    @Autowired
    private SellerService sellerService;
    @Autowired
    private ProductService prodService;
    @Autowired
    private LocationService locService;
    @Autowired
    private AssetService assetService;
    @Autowired
    private LotRepo lotRepo;
    @Autowired
    private AssetRepo assetRepo;
    @Autowired
    private EmailService emailService;

    @GetMapping("/")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("employeeId") Long employeeId, @RequestParam("password") String password, Model model, HttpServletRequest request) {
        // Log the received credentials
        System.out.println("Attempting login for employee ID: " + employeeId);
        

        Employee oauthUser = empService.login(employeeId, password);

        if (oauthUser != null) {
            // Log successful login
            System.out.println("Login successful for employee ID: " + oauthUser.getEmployeeId());

            // Store user in session
            HttpSession session = request.getSession();
            session.setAttribute("user", oauthUser);
            session.removeAttribute("selectedLocationId");
            // Redirect based on user role
            if ("A".equals(oauthUser.getRole())) {
                return "redirect:/admin-dashboard";
            } else {
                return "redirect:/employee-dashboard";
            }
        } else {
            // Log failed login
            System.out.println("Invalid username or password for employee ID: " + employeeId);

            // If login fails, show error message
            model.addAttribute("errorMessage", "Invalid username or password.");
            return "login";
        }
    }
    
    @GetMapping("/select-location")
    public String selectLocation(Model model) {
        model.addAttribute(
                "locations",
                locService.listAll());
        return "select-location";
    }
    
    @PostMapping("/set-location")
    public String setLocation(
            @RequestParam Long locationId,
            HttpSession session) {
        session.setAttribute(
                "selectedLocationId",
                locationId);
        return "redirect:/admin-dashboard";
    }
    
    @GetMapping("/adduser")
    public String add(Model model) {
        model.addAttribute("employee", new Employee());
        return "adduser";
    }
    
    @PostMapping("/save")
    public String saveStudent(@ModelAttribute("employee") Employee std, HttpServletRequest request) {
        empService.save(std);
        return "admin-dashboard";
    }
    
    @GetMapping("/employee")
    public String listEmployees(Model model) {
        List<Employee> employees = empService.listAll();
        model.addAttribute("employees", employees);
        return "employee";
    }
//    @PostMapping("/upload")
//    public String uploadCSV(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
//        if (file.isEmpty()) {
//            redirectAttributes.addFlashAttribute("message", "Please select a CSV file to upload.");
//            return "redirect:/employees";
//        }
//
//        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
//            List<Employee> employees = new ArrayList<>();
//            String line;
//            boolean firstLine = true;
//
//            while ((line = reader.readLine()) != null) {
//                if (firstLine) {
//                    firstLine = false; // Skip header line
//                    continue;
//                }
//                String[] fields = line.split(",");
//                Employee employee = new Employee();
//                employee.setEmployeeId(Long.parseLong(fields[0]));
//                employee.setName(fields[1]);
//                employee.setDate_of_joining(Date.valueOf(fields[2]));
//                employee.setPassword(fields[3]);
//                employee.setPhone_no(fields[4]);
//                employee.setRole(fields[5]);
//                employees.add(employee);
//            }
//            empService.saveAll(employees);
//            redirectAttributes.addFlashAttribute("message", "File uploaded successfully!");
//        } catch (Exception e) {
//            redirectAttributes.addFlashAttribute("message", "An error occurred while processing the CSV file: " + e.getMessage());
//        }
//
//        return "redirect:/employees";
//    }

//    @PostMapping("/employees/upload")
//    public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
//        if (file.isEmpty()) {
//            redirectAttributes.addFlashAttribute("message", "Please select a file to upload.");
//            return "redirect:uploadStatus";
//        }
//
//        try {
//            empService.saveEmployeesFromFile(file);
//            redirectAttributes.addFlashAttribute("message", "You successfully uploaded " + file.getOriginalFilename() + "!");
//        } catch (Exception e) {
//            redirectAttributes.addFlashAttribute("message", "Failed to upload file: " + e.getMessage());
//        }
//
//        return "redirect:/uploadStatus";
//    }
//   
    @GetMapping("/employees/{employeeId}/edit")
    public ModelAndView showEditEmployeePage(@PathVariable("employeeId") Long employeeId) {
        ModelAndView mav = new ModelAndView("new"); 
        Employee employee = empService.getByEmployeeId(employeeId); // Call the service method to get employee by email
        mav.addObject("employee", employee);
        return mav;
    }
    
    @GetMapping("/employees/{id}/assets")
    public String viewEmployeeAssets(@PathVariable("id") long emp_id, Model model) {
        List<Asset> assets = assetService.findByEmployeeId(emp_id);
        model.addAttribute("assets", assets);
        model.addAttribute("employeeId", emp_id);  // Add employeeId to the model
        return "employee_assets";
    }
    
    @GetMapping("/employees/{employeeId}/delete")
    public String deleteEmployee(@PathVariable("employeeId") Long employeeId) {
        // Delete employee from the database
        empService.deleteByEmployeeId(employeeId);
        return "redirect:/employee";
    }

    @PostMapping("/savechanges")
    public String saveEmployeeChanges(@ModelAttribute("employee") Employee employee) {
    	Employee existingEmployee = empService.getByEmployeeId(employee.getEmployeeId());
        
        // Updating fields that are allowed to change
        existingEmployee.setEmployeeId(employee.getEmployeeId());
        existingEmployee.setName(employee.getName());
        existingEmployee.setDate_of_joining(employee.getDate_of_joining());
        existingEmployee.setPhone_no(employee.getPhone_no());
        existingEmployee.setRole(employee.getRole());
        
        // Save the updated employee data
        empService.save(existingEmployee);
        return "redirect:/employee"; 
    }
    
    @GetMapping("/admin-dashboard")
    public String adminDashboard(Model model,HttpSession session) {
    	
    	Long selectedLocationId =
    	        (Long) session.getAttribute(
    	                "selectedLocationId");
    	 if(selectedLocationId == null) {
    	        return "redirect:/select-location";
    	    }
    	List<Asset> assets =
    	        assetService.listAll()
    	                .stream()
    	                .filter(a ->
    	                        a.getLocation() != null &&
    	                        a.getLocation()
    	                         .getLocationId()
    	                         .equals(selectedLocationId))
    	                .toList();
    	long warrantyExpiredCount =
    	        assets.stream()
    	              .filter(a -> a.getProduct() != null)
    	              .filter(a -> {
    	                  if(a.getProduct()
    	                      .getPurchaseDate() == null)
    	                      return false;
    	                  LocalDate expiryDate =
    	                          a.getProduct()
    	                           .getPurchaseDate()
    	                           .toLocalDate()
    	                           .plusYears(
    	                                   a.getProduct()
    	                                    .getWarranty());
    	                  return expiryDate.isBefore(
    	                          LocalDate.now());
    	              })
    	              .count();
    	long assignedCount =
    	        assets.stream()
    	              .filter(a -> "Assigned".equalsIgnoreCase(a.getStatus()))
    	              .count();

    	long serviceCount =
    	        assets.stream()
    	              .filter(a -> "Servicing Required".equalsIgnoreCase(a.getStatus()))
    	              .count();
    	long unassignedCount =
    	        assets.stream()
    	              .filter(a -> a.getEmployee() == null)
    	              .count();
    	model.addAttribute("statusActive", assignedCount);
    	model.addAttribute("statusService", serviceCount);
    	model.addAttribute("assetCount",assets.size());
    	boolean hasAssets = !assets.isEmpty();
    	model.addAttribute("hasAssets", hasAssets);
    	model.addAttribute("statusWarranty",warrantyExpiredCount);
    	model.addAttribute("statusUnassigned", unassignedCount);
    	Map<String, Long> categoryMap =
    	        assets.stream()
    	              .filter(a -> a.getProduct() != null)
    	              .collect(Collectors.groupingBy(
    	                      a -> a.getProduct().getCategoryName(),
    	                      Collectors.counting()
    	              ));
    	List<Integer> serviceTrend = new ArrayList<>();
    	List<Integer> warrantyTrend = new ArrayList<>();
    	for (int month = 1; month <= 12; month++) {
    	    int currentMonth = month;
    	    long count =
    	            assets.stream()
    	                    .filter(a ->
    	                            "Servicing Required"
    	                                    .equalsIgnoreCase(
    	                                            a.getStatus()))
    	                    .filter(a -> a.getPurchaseDate() != null)
    	                    .filter(a ->
    	                            a.getPurchaseDate()
    	                                    .getMonthValue()
    	                                    == currentMonth)
    	                    .count();
    	    serviceTrend.add((int) count);
    	}
    	for (int month = 1; month <= 12; month++) {
    	    int currentMonth = month;
    	    long count =
    	            assets.stream()
    	                    .filter(a -> a.getPurchaseDate() != null)
    	                    .filter(a -> a.getWarrantyYears() != null)
    	                    .filter(a -> {
    	                    	LocalDate expiryDate =
    	                                a.getPurchaseDate()
    	                                 .plusYears(
    	                                     a.getWarrantyYears());
    	                        return expiryDate.getYear()
    	                                == LocalDate.now().getYear()
    	                               &&
    	                               expiryDate.getMonthValue()
    	                                == currentMonth;
    	                    })
    	                    .count();
    	    warrantyTrend.add((int) count);
    	}
    	model.addAttribute("categoryLabels",new ArrayList<>(categoryMap.keySet()));
    	model.addAttribute("categoryCounts",new ArrayList<>(categoryMap.values()));
    	model.addAttribute("lineLabels",List.of("Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"));
    	model.addAttribute("recentAssets",assetRepo.findTop10ByLocation_LocationIdOrderByAssetIdDesc(selectedLocationId));
    	model.addAttribute("selectedLocation",locService.getLocationById(selectedLocationId));
    	model.addAttribute("warrantyExpiredCount",warrantyExpiredCount);
    	model.addAttribute("employeeCount", empService.countEmployees());
    	model.addAttribute("locationCount", locService.countLocations());
    	model.addAttribute("serviceRequiredCount", assetService.getServicingRequiredCount());
    	model.addAttribute("barLabels",List.of("Jan","Feb","Mar","Apr","May","June","July","Aug","Sep","Oct","Nov","Dec"));
    	model.addAttribute("barData",List.of(0,0,0,0,0,0,0,0,0,0,0,0));
    	model.addAttribute("lineServiceData", serviceTrend);
    	model.addAttribute("lineWarrantyData", warrantyTrend);
    	model.addAttribute("selectedLocation",locService.getLocationById(selectedLocationId));
        return "admin-dashboard";
    }
    
    @GetMapping("/assets-requiring-service")
    public String getAssetsRequiringServicing(
            Model model,
            HttpSession session) {
        Long selectedLocationId =(Long) session.getAttribute("selectedLocationId");
        if(selectedLocationId == null) {
            return "redirect:/select-location";
        }
        List<Asset> assetsRequiringService =assetService.getAssetsRequiringService().stream()
                        .filter(a ->
                                a.getLocation() != null &&
                                a.getLocation()
                                 .getLocationId()
                                 .equals(selectedLocationId))
                        .collect(Collectors.toList());
        model.addAttribute("assets",assetsRequiringService);
        model.addAttribute("selectedLocation",locService.getLocationById(selectedLocationId));
        return "assets-requiring-service";
    }
    
    @PostMapping("/asset/{id}/complete-service")
    public String completeService(@PathVariable("id") Long assetId, RedirectAttributes redirectAttributes) {
        boolean updated = assetService.updateAssetStatusToAssigned(assetId);
        if (updated) {
            redirectAttributes.addFlashAttribute("message", "Asset status updated successfully.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Failed to update asset status.");
        }
        return "redirect:/assets-requiring-service";
    }
    
    
    @GetMapping("/listwarranty")
    public String listWarrantyExpired(
            Model model,
            HttpSession session) {

        Long selectedLocationId =(Long) session.getAttribute("selectedLocationId");
        List<Asset> expiredAssets =assetService.findByLocationId(selectedLocationId).stream().filter(asset -> {
                    Product product =asset.getProduct();
                    if(product == null || product.getPurchaseDate() == null)
                        return false;
                    LocalDate expiryDate = product.getPurchaseDate().toLocalDate().plusYears(product.getWarranty());
                    return expiryDate.isBefore(
                            LocalDate.now());
                })
                .collect(Collectors.toList());
        
        model.addAttribute("expiredAssets",expiredAssets);
        return "warrantyExpired";
    }

    @GetMapping("/listassets")
    public String listAssets(
            Model model,
            HttpSession session) {

        Long selectedLocationId =
                (Long) session.getAttribute(
                        "selectedLocationId");

        if(selectedLocationId == null) {
            return "redirect:/select-location";
        }

        List<Asset> assets =
                assetService.findByLocationId(
                        selectedLocationId);

        List<Product> expiredProducts =
                assets.stream()
                      .map(Asset::getProduct)
                      .filter(p -> p != null)
                      .filter(product -> {

                          if(product.getPurchaseDate() == null)
                              return false;
                          LocalDate purchaseDate =
                                  product.getPurchaseDate()
                                         .toLocalDate();
                          LocalDate warrantyExpiryDate =
                                  purchaseDate.plusYears(
                                          product.getWarranty());
                          return warrantyExpiryDate.isBefore(
                                  LocalDate.now());
                      })
                      .collect(Collectors.toList());

        model.addAttribute("assets",assets);
        model.addAttribute("expiredProducts",expiredProducts);
        return "listassets";
    }
    
    @GetMapping("/employee-dashboard")
    public String employeeDashboard() {
        return "employee-dashboard";
    }
    
    @GetMapping("/product")
    public String ProductDashboard() {
        return "product";
    }
    

    @GetMapping("/listproduct")
    public ModelAndView listProducts(Model model) {
        List<Product> products = prodService.getAllProducts();
        model.addAttribute("products", products);
        return new ModelAndView("listproduct");
    }
    
    @GetMapping("/newproduct")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("sellers", sellerService.listAll());
        return "newproduct";
    }

    @PostMapping("/saveproduct")
    public String saveProduct(@ModelAttribute @Valid ProductDTO productDTO,
                              BindingResult bindingResult,
                              Model model) {
        if (bindingResult.hasErrors()) {
            // Handle validation errors
            return "product-form";
        }
        System.out.println("Report1: " + productDTO.getReport1().getOriginalFilename());
        System.out.println("Report2: " + productDTO.getReport2().getOriginalFilename());
        System.out.println("Image: " + productDTO.getImage().getOriginalFilename());

        // Call service layer to process the productDTO
        prodService.createProduct(productDTO);

        // Redirect or show success message
        return "redirect:listproduct";
    }
    
    @GetMapping("/product/report/{productId}")
    public ResponseEntity<byte[]> viewReport1(@PathVariable Long productId) {
        System.out.println("Request received for report1 of Product ID: " + productId);
        byte[] report1 = prodService.getReport1ById(productId);
        if (report1 != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF); // Adjust content type if not PDF
            headers.setContentDispositionFormData("inline", "report1.pdf");
            return new ResponseEntity<>(report1, headers, HttpStatus.OK);
        } else {
            System.out.println("Report1 not found for Product ID: " + productId);
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/product/image/{productId}")
    public ResponseEntity<byte[]> viewImage(@PathVariable Long productId) {
        byte[] image = prodService.getImageById(productId);
        if (image != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG); // Adjust content type based on your image format
            headers.setContentDispositionFormData("attachment", "product_image.jpeg"); // Force download with 'attachment'
            return new ResponseEntity<>(image, headers, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("product/edit/{productId}")
    public String editProductForm(@PathVariable Long productId, Model model) {
        ProductDTO productDTO = prodService.getProductDTOById(productId);
        model.addAttribute("product", productDTO); // "product" is the model attribute name
        return "editproduct"; // Return the name of your Thymeleaf template (edit-product.html)
    }

    @PostMapping("/updateproduct/{productId}")
    public String updateProduct(@PathVariable Long productId, @ModelAttribute("product") ProductDTO productDTO) {
        // Ensure productDTO has necessary attributes and is correctly populated from the form
        prodService.updateProduct(productId, productDTO);
        return "redirect:/listproduct";
    }
    @GetMapping("product/{productId}/delete")
    public String deleteProduct(@PathVariable Long productId) {
        // Logic to delete product
        prodService.deleteProduct(productId);
        return "listproduct";
    }
    
    @GetMapping("/seller")
    public String SellerDashboard() {
        return "seller";
    }
    
    
    @GetMapping("/sellerform")
    public String showForm(Model model) {
      model.addAttribute("seller", new Seller());
      return "newseller";
    }
    
    @PostMapping("/savenewseller")
    public String createSeller(@ModelAttribute Seller seller) {
      sellerService.save(seller);
      return "redirect:/newseller";
    }
    
    @GetMapping("/listallseller")
    public String listSeller(Model model) {
        List<Seller> seller = sellerService.listAll();
        model.addAttribute("sellers", seller);
        return "listallseller";
    }
    
    @GetMapping("/seller/{SellerId}/edit")
    public ModelAndView showEditSellerPage(@PathVariable("SellerId") String SellerId) {
        ModelAndView mav = new ModelAndView("editseller"); 
        Seller seller = sellerService.getSellerById(SellerId); // Call the service method to get employee by email
        mav.addObject("seller", seller);
        return mav;
    }
    
    @GetMapping("/seller/{SellerId}/delete")
    public String deleteSeller(@PathVariable("SellerId") String SellerId) {
        // Delete employee from the database
        sellerService.deleteSeller(SellerId);
        return "redirect:/listallseller";
    }

    @PostMapping("/savesellerchanges")
    public String saveSellerChanges(@ModelAttribute("seller") Seller seller) {
    	Seller existingSeller = sellerService.getSellerById(seller.getSellerId());
        
        // Updating fields that are allowed to change
        existingSeller.setSellerId(seller.getSellerId());
        existingSeller.setCompanyName(seller.getCompanyName());
        existingSeller.setContactNo(seller.getContactNo());
        existingSeller.setEmailId(seller.getEmailId());
        existingSeller.setAddress(seller.getAddress());
        
        // Save the updated employee data
        sellerService.save(existingSeller);
        return "redirect:/listallseller"; 
    }
    
    @GetMapping("/addlocation")
    public String addLocationPage() {
        return "addlocation";
    }
    
    @GetMapping("/listlocation")
    public String listLocations(
            Model model,
            HttpSession session) {
        model.addAttribute(
                "locations",
                locService.listAll());
        Long selectedLocationId =
                (Long) session.getAttribute(
                        "selectedLocationId");
        if(selectedLocationId != null){
            model.addAttribute(
                    "selectedLocation",
                    locService.getLocationById(
                            selectedLocationId));
        }
        return "listlocation";
    }

    @GetMapping("/newlocation")
    public String newLocationPage(Model model) {
    	
    	model.addAttribute(
    			"location",
    			new Location()
    			);
        return "newlocation";
    }

    @PostMapping("/savenewlocation")
    public String saveNewLocation(@RequestParam("location_name") String location_name,
                                  Model model) {
        Location location = new Location();
        location.setLocation_name(location_name);

        locService.save(location);

        return "redirect:/addlocation";
    }
    
    @GetMapping("/location/{locationId}/edit")
    public ModelAndView showEditlocationPage(@PathVariable("locationId") Long locationId) {
        ModelAndView mav = new ModelAndView("editlocation"); 
        Location location = locService.getLocationById(locationId); // Call the service method to get employee by email
        mav.addObject("location", location);
        return mav;
    }
    
    @PostMapping("/savelocationchanges")
    public String saveLocationChanges(@ModelAttribute("location") Location location) {
    	Location existingLocation = locService.getLocationById(location.getLocationId());
        
        // Updating fields that are allowed to change
        existingLocation.setLocationId(location.getLocationId());
        existingLocation.setLocation_name(location.getLocation_name());
        // Save the updated employee data
        locService.save(existingLocation);
        return "redirect:/listlocation"; 
    }
    
    @GetMapping("/location/{locationId}/delete")
    public String deleteLocation(@PathVariable("locationId") Long locationId) {
        // Delete employee from the database
        locService.deleteLocation(locationId);
        return "redirect:/listlocation";
    }
    
    @GetMapping("/addasset")
    public String addAssetPage() {
    	return "addasset";
    }

    @GetMapping("/addnewasset")
    public String showAddAssetForm(Model model) {
        model.addAttribute("asset", new Asset());
        model.addAttribute(
                "locations",
                locService.listAll());
        model.addAttribute(
                "products",
                prodService.getAllProducts());
        model.addAttribute(
                "employees",
                empService.listAll());
        return "addnewasset";
    }

    // Mapping to handle form submission and save asset
    @PostMapping("/saveasset")
    public String saveAsset(
            @ModelAttribute("asset") Asset asset) {
        Product product =
                prodService.getProductById(
                        asset.getProduct().getProductId());
        asset.setWarrantyYears(
                product.getWarranty());
        assetService.save(asset);
        Employee employee =
                empService.getByEmployeeId(
                        asset.getEmployee().getEmployeeId());
        if(employee != null &&
           employee.getEmail() != null &&
           !employee.getEmail().isBlank()) {
            String subject = "New Asset Assigned";
            String body =
                    "Dear " + employee.getName() + ",\n\n" +
                    "A new asset has been assigned to you.\n\n" +
                    "Asset ID: " + asset.getAssetId() + "\n" +
                    "Product: " + product.getProductName() + "\n" +
                    "Assignment Date: " + java.time.LocalDate.now() + "\n\n" +
                    "Regards,\nCMPDI Asset Management System";
            emailService.sendEmail(
                    employee.getEmail(),
                    subject,
                    body);
        }
        return "redirect:/listassets";
    }

    // Mapping to fetch all assets and display them
//    @GetMapping("/listassets")
//    public String showAllAssets(Model model) {
//        List<Asset> assets = assetService.listAll();
//        model.addAttribute("assets", assets);
//        return "listassets"; // Thymeleaf template name
//    }
    
    @GetMapping("/downloadassets")
    public ResponseEntity<byte[]> downloadAssets() {
        List<Asset> assets = assetService.listAll();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(out);

        // Write CSV headers
        writer.println("Asset ID,Product ID,Product Name,Employee ID,Employee Name,Location,Status");

        // Write asset data
        for (Asset asset : assets) {
            String assetId =
                    asset.getAssetId() != null
                            ? asset.getAssetId().toString()
                            : "N/A";
            String productId =
                    asset.getProduct() != null
                            ? String.valueOf(asset.getProduct().getProductId())
                            : "N/A";
            String productName =
                    asset.getProduct() != null
                            ? asset.getProduct().getProductName()
                            : "N/A";
            String employeeId =
                    asset.getEmployee() != null
                            ? String.valueOf(asset.getEmployee().getEmployeeId())
                            : "Unassigned";
            String employeeName =
                    asset.getEmployee() != null
                            ? asset.getEmployee().getName()
                            : "Unassigned";
            String locationName =
                    asset.getLocation() != null
                            ? asset.getLocation().getLocation_name()
                            : "N/A";
            String status =
                    asset.getStatus() != null
                            ? asset.getStatus()
                            : "N/A";

            writer.println(
                    assetId + "," +
                    productId + "," +
                    productName + "," +
                    employeeId + "," +
                    employeeName + "," +
                    locationName + "," +
                    status
            );
        }

        writer.flush();

        byte[] data = out.toByteArray();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(
        	    MediaType.parseMediaType("text/csv")
        	);
        headers.setContentDispositionFormData("attachment", "assets.csv");
        headers.setContentLength(data.length);

        return ResponseEntity.ok()
                .headers(headers)
                .body(data);
    }
    
    @PostMapping("/asset/{id}/service")
    public String sendForServicing(@PathVariable("id") Long assetId, RedirectAttributes redirectAttributes) {
        try {
            assetService.updateStatus(assetId, "Servicing Required");
            redirectAttributes.addFlashAttribute("message", "Asset sent for servicing successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error sending asset for servicing: " + e.getMessage());
        }
        return "redirect:/listassets"; // Redirect to the assets list page
    }
    
    @GetMapping("/downloadassets/pdf")
    public ResponseEntity<byte[]> downloadAssetsPdf() {
        List<Asset> assets = assetService.listAll();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(out);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph("Assets List"));

        Table table = new Table(new float[]{1, 1, 1, 1, 1, 1, 1});
        table.addCell("Asset ID");
        table.addCell("Product ID");
        table.addCell("Product Name");
        table.addCell("Employee ID");
        table.addCell("Employee Name");
        table.addCell("Location ID");
        table.addCell("Status");

        for (Asset asset : assets) {
            table.addCell(String.valueOf(asset.getAssetId()));
            table.addCell(String.valueOf(asset.getProduct().getProductId()));
            table.addCell(asset.getProduct().getProductName());
            table.addCell(String.valueOf(asset.getEmployee().getEmployeeId()));
            table.addCell(asset.getEmployee().getName());
            table.addCell(asset.getLocation().getLocation_name());
            table.addCell(asset.getStatus());
        }

        document.add(table);
        document.close();

        byte[] data = out.toByteArray();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "assets.pdf");
        headers.setContentLength(data.length);

        return ResponseEntity.ok()
                .headers(headers)
                .body(data);
    }
    
    @GetMapping("/product/presentIn/{productId}")
    public String getProductLocations(@PathVariable Long productId, Model model) {
        List<Location> locations = assetService.getProductLocations(productId);
        model.addAttribute("locations", locations);
        return "presentIn";
}
    @PostMapping("/product/presentIn/{productId}/generate-pdf")
    public void generatePdf(@PathVariable Long productId, HttpServletResponse response) throws IOException {
        // Retrieve product, location, and asset data
        Product product = prodService.getProductById(productId);  // Fetch product details
        if (product == null) {
            System.out.println("No product found with ID: " + productId);
            return;
        }
        List<Location> locations = assetService.getProductLocations(productId);  // Get locations for the product
        if (locations == null || locations.isEmpty()) {
            System.out.println("No locations found for product ID: " + productId);
            return;
        }
        List<Asset> assets = assetService.listAll();  // Get all assets to filter below
        if (assets == null || assets.isEmpty()) {
            System.out.println("No assets found.");
            return;
        }

        // Set the response headers
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=Product_Locations_" + productId + ".pdf");

        // Create the PDF document
        PdfWriter writer = new PdfWriter(response.getOutputStream());
        com.itextpdf.kernel.pdf.PdfDocument pdf = new com.itextpdf.kernel.pdf.PdfDocument(writer);
        Document document = new Document(pdf);

        // Add product information
        document.add(new Paragraph("Product Information").setBold().setFontSize(16));
        document.add(new Paragraph("Product ID: " + productId));
        document.add(new Paragraph("Product Name: " + product.getProductName()));
        document.add(new Paragraph("Seller ID: " + product.getSellerId()));  // Assuming Product has a Seller entity
        document.add(new Paragraph(" "));

        // Create the table with columns for location and asset details
        Table table = new Table(new float[]{1, 2, 1, 2, 2});
        table.addHeaderCell(new Cell().add(new Paragraph("Location ID")));
        table.addHeaderCell(new Cell().add(new Paragraph("Location Name")));
        table.addHeaderCell(new Cell().add(new Paragraph("Asset ID")));
        table.addHeaderCell(new Cell().add(new Paragraph("Asset Status")));
        table.addHeaderCell(new Cell().add(new Paragraph("Product Name")));

        // Populate the table with location and asset data
        for (Location location : locations) {
            System.out.println("Processing location ID: " + location.getLocationId());
            List<Asset> locationAssets = assets.stream()
                .filter(asset -> asset.getLocation().getLocationId().equals(location.getLocationId()) && asset.getProduct().getProductId().equals(productId))
                .collect(Collectors.toList());

            if (locationAssets.isEmpty()) {
                System.out.println("No assets found for location ID: " + location.getLocationId());
                continue;
            }

            for (Asset asset : locationAssets) {
                table.addCell(new Cell().add(new Paragraph(location.getLocationId().toString())));
                table.addCell(new Cell().add(new Paragraph(location.getLocation_name())));
                table.addCell(new Cell().add(new Paragraph(asset.getAssetId().toString())));
                table.addCell(new Cell().add(new Paragraph(asset.getStatus())));
                table.addCell(new Cell().add(new Paragraph(product.getProductName())));
            }
        }

        // Add the table to the document
        document.add(table);

        // Close the document
        document.close();
    }
    
    @GetMapping("/product/search")
    public String searchProducts(@RequestParam("keyword") String keyword, Model model) {
        List<Product> products = prodService.searchProducts(keyword);
        model.addAttribute("products", products);
        model.addAttribute("keyword", keyword);
        return "listproduct";
    }
    @GetMapping("/seller/search")
    public String searchSeller(
            @RequestParam("keyword") String keyword,
            Model model) {

        List<Seller> sellers = sellerService.searchSeller(keyword);

        model.addAttribute("sellers", sellers);
        model.addAttribute("keyword", keyword);

        return "listallseller";
    }
    @GetMapping("/downloadasset")
    public ResponseEntity<byte[]> downloadAssetss(HttpSession session) {
    	Long selectedLocationId =
    		    (Long) session.getAttribute(
    		        "selectedLocationId");

    		List<Asset> assets =
    		    assetService.listAll()
    		        .stream()
    		        .filter(a ->
    		            a.getLocation() != null &&
    		            a.getLocation()
    		                .getLocationId()
    		                .equals(selectedLocationId))
    		        .toList();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(out);

        // Write CSV headers
        writer.println("Asset ID,Product ID,Product Name,Employee ID,Employee Name,Location,Status");

        // Write asset data
        for (Asset asset : assets) {
        	writer.printf(
        		    "%s,%s,\"%s\",%s,\"%s\",\"%s\",\"%s\"%n",
        		    asset.getAssetId(),
        		    asset.getProduct().getProductId(),
        		    asset.getProduct().getProductName(),
        		    asset.getEmployee().getEmployeeId(),
        		    asset.getEmployee().getName(),
        		    asset.getLocation().getLocation_name(),
        		    asset.getStatus()
        		);
        }

        writer.flush();

        byte[] data = out.toByteArray();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("text/csv"));
        headers.setContentDispositionFormData("attachment", "assets.csv");
        headers.setContentLength(data.length);

        return ResponseEntity.ok()
                .headers(headers)
                .body(data);
    }

    // Mapping to download assets as PDF
    @GetMapping("/downloadasset/pdf")
    public ResponseEntity<byte[]> downloadAssetsPdff(HttpSession session) {
    	Long selectedLocationId =
    		    (Long) session.getAttribute(
    		        "selectedLocationId");
    		List<Asset> assets =
    		    assetService.listAll()
    		        .stream()
    		        .filter(a ->
    		            a.getLocation() != null &&
    		            a.getLocation()
    		                .getLocationId()
    		                .equals(selectedLocationId))
    		        .toList();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(out);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph("Assets List"));
        document.add(
        	    new Paragraph("Asset Inventory Report")
        	        .setBold()
        	        .setFontSize(18)
        	);

        	document.add(
        	    new Paragraph(
        	        "Generated on: "
        	        + LocalDate.now()
        	    )
        	);
        	Table table = new Table(
        		        new float[]{1.2f,1.2f,2.5f,1.2f,2.5f,2f,1.5f}
        		);
        table.addCell("Asset ID");
        table.addCell("Product ID");
        table.addCell("Product Name");
        table.addCell("Employee ID");
        table.addCell("Employee Name");
        table.addCell("Location ID");
        table.addCell("Status");

        for (Asset asset : assets) {
            table.addCell(String.valueOf(asset.getAssetId()));
            table.addCell(
                asset.getProduct() != null
                    ? String.valueOf(asset.getProduct().getProductId())
                    : "N/A"
            );
            table.addCell(
                asset.getProduct() != null
                    ? asset.getProduct().getProductName()
                    : "N/A"
            );
            table.addCell(
                asset.getEmployee() != null
                    ? String.valueOf(asset.getEmployee().getEmployeeId())
                    : "Unassigned"
            );
            table.addCell(
                asset.getEmployee() != null
                    ? asset.getEmployee().getName()
                    : "Unassigned"
            );
            table.addCell(
                asset.getLocation() != null
                    ? asset.getLocation().getLocation_name()
                    : "N/A"
            );
            table.addCell(
                asset.getStatus() != null
                    ? asset.getStatus()
                    : "N/A"
            );
        }

        document.add(table);
        document.close();

        byte[] data = out.toByteArray();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "assets.pdf");
        headers.setContentLength(data.length);

        return ResponseEntity.ok()
                .headers(headers)
                .body(data);
    }
    
    @GetMapping("/product/{productId}/generate-pdf")
    public ResponseEntity<byte[]> downloadProductPdff() {
        List<Product> prods = prodService.getAllProducts();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(out);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph("Product Description"));

        Table table = new Table(new float[]{1, 1, 1});
        table.addCell("Product ID");
        table.addCell("Product Name");
        table.addCell("Seller Id");
      
        for (Product prod : prods) {
            table.addCell(String.valueOf(prod.getProductId()));
            table.addCell(String.valueOf(prod.getProductName()));
            table.addCell(String.valueOf(prod.getSellerId()));
           
        }

        document.add(table);
        document.close();

        byte[] data = out.toByteArray();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "productInfo.pdf");
        headers.setContentLength(data.length);

        return ResponseEntity.ok()
                .headers(headers)
                .body(data);
    }
    
    
    @GetMapping("/asset/{assetId}/edit")
    public String showEditAssetPage(@PathVariable("assetId") Long assetId, Model model) {
    	Asset asset = assetService.getAssetById(assetId);
        model.addAttribute("asset", asset);
        model.addAttribute("product", asset.getProduct());
        model.addAttribute("location", asset.getLocation());
        model.addAttribute("locations", locService.listAll());
        model.addAttribute("employee", asset.getEmployee());
        model.addAttribute("status", asset.getStatus());
        // Add any other necessary model attributes
        return "editasset"; // Thymeleaf template name for editing asset
    }

    // Mapping to save edited asset changes
    @PostMapping("/updateasset/{assestId}")
    public String saveAssetChanges(@PathVariable("assetId") Long assetId,@ModelAttribute("asset") Asset asset) {
        Asset existingAsset = assetService.getAssetById(assetId);

        // Update fields that are allowed to change
        existingAsset.setAssetId(asset.getAssetId());
        existingAsset.setProduct(asset.getProduct());
        existingAsset.setEmployee(asset.getEmployee());
        existingAsset.setLocation(asset.getLocation());
        existingAsset.setStatus(asset.getStatus());

        // Save updated asset data
        assetService.save(existingAsset);

        return "redirect:/listassets"; // Redirect to list assets page after saving changes
    }

//     Mapping to delete an asset
    @GetMapping("/asset/{assetId}/delete")
    public String deleteAsset(@PathVariable("assetId") Long assetId) {
        assetService.deleteAsset(assetId);
        return "redirect:/listassets"; // Redirect to list assets page after deletion
    }
    
    @GetMapping("/newseller")
    public String newSeller(Model model) {
        model.addAttribute("totalSellers",
                sellerService.countAllSellers());
        return "newseller";
    }
    
    @GetMapping("/product/toggle-status/{id}")
    public String toggleProductStatus(
            @PathVariable Long id){
        Product product =
                prodService.getProductById(id);
        if(product != null){
            if("Active".equalsIgnoreCase(
                    product.getCatalogueStatus())){
                product.setCatalogueStatus(
                        "Inactive");
            }else{
                product.setCatalogueStatus(
                        "Active");
            }
            prodService.save(product);
        }
        return "redirect:/product";
    }
    
    
    @GetMapping("/assign-lot")
    public String assignLotPage(Model model,HttpSession session){

        Long selectedLocationId =
                (Long) session.getAttribute(
                        "selectedLocationId");

        if(selectedLocationId == null){
            return "redirect:/select-location";
        }

        List<Asset> eligibleAssets =
                assetService.findByLocationId(
                        selectedLocationId)
                .stream()
                .filter(asset -> {
                    Product product =
                            asset.getProduct();
                    if(product == null)
                        return false;
                    boolean warrantyExpired = false;
                    if(product.getPurchaseDate() != null){
                        LocalDate expiryDate =
                                product.getPurchaseDate()
                                       .toLocalDate()
                                       .plusYears(
                                               product.getWarranty());
                        warrantyExpired =
                                LocalDate.now()
                                         .isAfter(expiryDate);
                    }
                    boolean oldPc = false;
                    if(product.getPurchaseDate() != null){
                        oldPc =
                                "PC".equalsIgnoreCase(
                                        product.getCategoryName())
                                &&
                                product.getPurchaseDate()
                                       .toLocalDate()
                                       .isBefore(
                                               LocalDate.now()
                                                        .minusYears(3));
                    }
                    return warrantyExpired || oldPc;
                })
                .toList();
        model.addAttribute("lots",lotRepo.findAll());
        model.addAttribute("assets",eligibleAssets);
        model.addAttribute("selectedLocation",locService.getLocationById(selectedLocationId));
        return "assign-lot";
    }

    @PostMapping("/assign-lot")
    public String assignLot(
            @RequestParam List<Long> assetIds){
        String lotNumber =
                "LOT-" +
                Year.now().getValue() +
                "-" +
                String.format("%03d",
                        lotRepo.count() + 1);
        Lot lot = new Lot();
        lot.setLotNumber(lotNumber);
        lot.setLocation("Main Office");
        lotRepo.save(lot);
        for(Long assetId : assetIds){
            Asset asset =assetService.getAssetById(assetId);
            asset.setLot(lot);
            assetService.save(asset);
        }
        return "redirect:/assign-lot";
    }
    
    @PostMapping("/delete-lot/{id}")
    public String deleteLot(
            @PathVariable Long id){
        Lot lot = lotRepo.findById(id).orElse(null);
        if(lot != null){
            List<Asset> assets =
                    assetRepo.findByLot_Id(id);
            for(Asset asset : assets){
                asset.setLot(null);
                assetRepo.save(asset);
            }
            lotRepo.delete(lot);
        }
        return "redirect:/assign-lot?success=deleted";
    }
    
    @GetMapping("/asset-details/{id}")
    public String assetDetails(
            @PathVariable Long id,
            Model model) {

        Asset asset = assetService.getAssetById(id);

        model.addAttribute("asset", asset);

        return "asset-details";
    }
    
    
//    @GetMapping("/asset-labels")
//    public String assetLabels(Model model) {
//
//        model.addAttribute(
//                "assets",
//                assetService.listAll());
//
//        return "asset-labels";
//    }
    @GetMapping("/asset-labels")
    public String assetLabels(Model model,HttpSession session){
        Long selectedLocationId =(Long) session.getAttribute("selectedLocationId");
        List<Asset> assets =assetService.findByLocationId(selectedLocationId);
        for(Asset a : assets){
            System.out.println(
                a.getAssetId() + " | " +
                a.getDepartment() + " | " +
                a.getLocation().getLocation_name() + " | " +
                a.getProduct().getProductName()
            );
        }
        model.addAttribute("assets", assets);
        return "asset-labels";
    }
    
}

