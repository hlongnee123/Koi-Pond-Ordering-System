package com.swp_group4.back_end.services;

import com.swp_group4.back_end.entities.*;
import com.swp_group4.back_end.enums.*;
import com.swp_group4.back_end.mapper.ConstructionOrderMapper;
import com.swp_group4.back_end.mapper.CustomerMapper;
import com.swp_group4.back_end.mapper.DesignMapper;
import com.swp_group4.back_end.mapper.QuotationMapper;
import com.swp_group4.back_end.repositories.*;
import com.swp_group4.back_end.requests.*;
import com.swp_group4.back_end.responses.*;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CustomerMapper customerMapper;
    @Autowired
    ConstructOrderRepository constructOrderRepository;
    @Autowired
    QuotationRepository quotationRepository;
    @Autowired
    PackageRepository packageRepository;
    @Autowired
    StaffRepository staffRepository;
    @Autowired
    ConstructionTasksRepository constructionTasksRepository;
    @Autowired
    PackageConstructionRepository packageConstructionRepository;
    @Autowired
    QuotationMapper quotationMapper;
    @Autowired
    DesignRepository designRepository;
    @Autowired
    PaymentOrderRepository paymentOrderRepository;
    @Autowired
    ConstructionTaskStaffRepository constructionTaskStaffRepository;
    @Autowired
    MaintenanceOrderRepository maintenanceOrderRepository;
    @Autowired
    ConstructionOrderMapper constructionOrderMapper;
    @Autowired
    DesignMapper designMapper;


    public void createCustomer(String accountId, CreateAccountRequest request) {
        customerRepository.save(Customer.builder()
                .accountId(accountId)
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phone(request.getPhone())
                .build());
    }

    public ServiceResponse<?> contactUs(ServiceRequest serviceRequest) {
        var context = SecurityContextHolder.getContext();
        String id = context.getAuthentication().getName();
        Customer customer = customerRepository.findByAccountId(id).orElseThrow();
        customerMapper.serviceRequestToCustomer(serviceRequest, customer);
        customerRepository.save(customer);
        if (serviceRequest.getService().name().equals("CONSTRUCTION_SERVICE")) {
            ConstructionOrder order = ConstructionOrder.builder()
                    .customerId(customer.getCustomerId())
                    .customerRequest(serviceRequest.getCustomerRequest())
                    .startDate(LocalDateTime.now())
                    .status(ConstructionOrderStatus.REQUESTED)
                    .build();
            constructOrderRepository.save(order);
        }
        if (serviceRequest.getService().name().equals("MAINTENANCE_SERVICE")) {
          MaintenanceOrder order = MaintenanceOrder.builder()
                  .customerId(customer.getCustomerId())
                  .status(MaintenanceOrderStatus.REQUESTED)
                  .build();
          maintenanceOrderRepository.save(order);
        }
        return null;
    }

    public CustomerResponse getOwnedInfo(){
        var context = SecurityContextHolder.getContext();
        String id = context.getAuthentication().getName();
        Customer customer = customerRepository.findByAccountId(id).orElseThrow();
        CustomerResponse response = new CustomerResponse();
        return customerMapper.customerToResponse(customer, response);
    }

    public AllCustomerInfoResponse getOwnedInfo(String accountId){
        Customer customer = customerRepository.findByAccountId(accountId).orElseThrow();
        AllCustomerInfoResponse response = new AllCustomerInfoResponse();
        return customerMapper.toAllCustomerInfoResponse(customer, response);
    }


    public CustomerResponse updateOwnedInfo(UpdateInfoRequest request, String accountId) {
        Customer customer = customerRepository.findByAccountId(accountId).orElseThrow();
        customerMapper.updateInfoToCustomer(request, customer);
        customerRepository.save(customer);
        CustomerResponse response = new CustomerResponse();
        return customerMapper.customerToResponse(customer, response);
    }

    public List<MaintenanceOrderResponse> listMaintenanceOrders(String accountId){
        Customer customer = customerRepository.findByAccountId(accountId).orElseThrow();
        List<MaintenanceOrder> orderList = maintenanceOrderRepository.findByCustomerId(customer.getCustomerId());
        List<MaintenanceOrderResponse> responses = new ArrayList<>();
        for (MaintenanceOrder order : orderList) {
            String paymentId = "";
            PaymentOrder payment = paymentOrderRepository.findPaymentOrdersByOrderId(order.getMaintenanceOrderId());
            if (payment != null) {
                paymentId = payment.getPaymentId();
            }
                MaintenanceOrderResponse response = MaintenanceOrderResponse.builder()
                    .customerName(customer.getFirstName() + " " + customer.getLastName())
                    .totalPrice(order.getTotal())
                    .status(order.getStatus())
                    .paymentId(paymentId)
                    .maintenanceOrderId(order.getMaintenanceOrderId())
                    .build();
            responses.add(response);
        }
        return responses;
    }

    public List<ConstructOrderDetailForCustomerResponse> listOrders(String accountId) {
        Customer customer = customerRepository.findByAccountId(accountId).orElseThrow();
        List<ConstructionOrder> orderList = constructOrderRepository.findByCustomerId(customer.getCustomerId());
        List<ConstructOrderDetailForCustomerResponse> responses = new ArrayList<>();
        for (ConstructionOrder order : orderList) {
            String designId = "";
            String quotationId = "";
            if (designRepository.findByDesignIdAndDesignStatusIn(order.getDesignId(), List.of(DesignStatus.CONFIRMED, DesignStatus.CONFIRMED_BY_USER)).isPresent()) {
                Design design = designRepository.findByDesignIdAndDesignStatusIn(order.getDesignId(), List.of(DesignStatus.CONFIRMED, DesignStatus.CONFIRMED_BY_USER)).orElseThrow();
                designId = design.getDesignId();
            }
            if (quotationRepository.findByQuotationIdAndQuotationStatusIn(order.getQuotationId(), List.of(QuotationStatus.CONFIRMED, QuotationStatus.CONFIRMED_BY_USER)).isPresent()) {
                Quotation quotation = quotationRepository.findByQuotationIdAndQuotationStatusIn(order.getQuotationId(),  List.of(QuotationStatus.CONFIRMED, QuotationStatus.CONFIRMED_BY_USER))
                        .orElseThrow();
                quotationId = quotation.getQuotationId();
            }
            ConstructOrderDetailForCustomerResponse response = ConstructOrderDetailForCustomerResponse.builder()
                    .customerName(customer.getFirstName() + " " + customer.getLastName())
                    .quotationId(quotationId)
                    .designId(designId)
                    .build();
            responses.add(constructionOrderMapper.toDetailForCustomerResponse(order, response));
        }
        return responses;
    }

    public ConstructQuotationResponse viewQuotation(String accountId, String constructionOrderId) {
        Customer customer = customerRepository.findByAccountId(accountId).orElseThrow();
        ConstructionOrder order = constructOrderRepository.findById(constructionOrderId).orElseThrow();
        Quotation quotation = quotationRepository.findById(order.getQuotationId())
                .orElseThrow(() -> new RuntimeException("Quotation not found"));
        Packages packages = packageRepository.findById(quotation.getPackageId()).orElseThrow();
        ConstructQuotationResponse response = ConstructQuotationResponse.builder()
                .constructOrderId(constructionOrderId)
                .customerName(customer.getFirstName() + " " + customer.getLastName())
                .consultantName(staffRepository.findById(order.getConsultantId()).orElseThrow().getStaffName())
                .packageType(packages.getPackageType())
                .priceStage1((order.getTotal() * quotation.getPercentageStage1())/100)
                .priceStage2((order.getTotal() * quotation.getPercentageStage2())/100)
                .priceStage3((order.getTotal() * quotation.getPercentageStage3())/100)
                .content(this.findContentOfTask(constructionOrderId))
                .build();
        constructionOrderMapper.toConstructQuotationResponse(order, response);
        quotationMapper.toQuotationResponse(quotation, response);
        return response;
    }

    private List<String> findContentOfTask(String constructionOrderId){
        List<ConstructionTasks> tasks = constructionTasksRepository.findByConstructionOrderId(constructionOrderId).orElseThrow();
        List<String> taskList = new ArrayList<>();
        for (ConstructionTasks task : tasks) {
            String content = packageConstructionRepository.findById(task.getPackageConstructionId()).orElseThrow().getContent();
            taskList.add(content);
        }
        return taskList;
    }

    public ConstructDesignResponse viewDesign(String constructionOrderId, String accountId) {
        ConstructionOrder order = constructOrderRepository.findById(constructionOrderId).orElseThrow();
        Design design = designRepository.findById(order.getDesignId()).orElseThrow();
        Customer customer = customerRepository.findByAccountId(accountId).orElseThrow();
        ConstructDesignResponse response =  ConstructDesignResponse.builder()
                .constructionOrderId(constructionOrderId)
                .customerName(customer.getFirstName() + " " + customer.getLastName())
                .designName(staffRepository.findById(order.getDesignerLeaderId()).orElseThrow().getStaffName())
                .customerRequest(order.getCustomerRequest())
                .constructionOrderStatus(order.getStatus())
                .build();
        return designMapper.toDesignResponse(design, response);
    }

    public StatusOfQuotationOrDesign<DesignStatus> confirmDesign(CustomerConfirmRequest<DesignStatus> request, String constructionOrderId, String accountId) {
        ConstructionOrder order = constructOrderRepository.findById(constructionOrderId).orElseThrow();
        Design design = designRepository.findById(order.getDesignId()).orElseThrow();
        if (request.getStatus().equals(DesignStatus.CONFIRMED)) {
            design.setDesignStatus(DesignStatus.CONFIRMED_BY_USER);
            designRepository.save(design);
            order.setStatus(ConstructionOrderStatus.CONFIRMED_DESIGN);
            constructOrderRepository.save(order);
            Customer customer = customerRepository.findByAccountId(accountId).orElseThrow();
            Quotation quotation = quotationRepository.findById(order.getQuotationId()).orElseThrow();
            PaymentOrder paymentOrder = PaymentOrder.builder()
                    .orderId(constructionOrderId)
                    .customerId(customer.getCustomerId())
                    .paymentTitle("Payment of the second stage")
                    .paidDate(LocalDateTime.now())
                    .dueDate(LocalDateTime.now().plusDays(7))
                    .total((long) (order.getTotal() * quotation.getPercentageStage2())/100)
                    .status(PaymentStatus.PENDING)
                    .build();
            paymentOrderRepository.save(paymentOrder);
        } else {
            design.setDesignStatus(DesignStatus.REJECTED);
            designRepository.save(design);
        }
        return StatusOfQuotationOrDesign.<DesignStatus>builder()
                .id(design.getDesignId())
                .status(design.getDesignStatus())
                .build();
    }

    public StatusOfQuotationOrDesign<QuotationStatus> confirmQuotation(CustomerConfirmRequest<QuotationStatus> request, String constructionOrderId, String accountId) {
        ConstructionOrder order = constructOrderRepository.findById(constructionOrderId).orElseThrow();
        Quotation quotation = quotationRepository.findById(order.getQuotationId()).orElseThrow();
        if (request.getStatus().equals(QuotationStatus.CONFIRMED)) {
            quotation.setQuotationStatus(QuotationStatus.CONFIRMED_BY_USER);
            quotationRepository.save(quotation);
            order.setStatus(ConstructionOrderStatus.CONFIRMED_QUOTATION);
            constructOrderRepository.save(order);
            Customer customer = customerRepository.findByAccountId(accountId).orElseThrow();
            PaymentOrder paymentOrder = PaymentOrder.builder()
                    .orderId(constructionOrderId)
                    .customerId(customer.getCustomerId())
                    .paymentTitle("Payment of the first stage")
                    .paidDate(LocalDateTime.now())
                    .dueDate(LocalDateTime.now().plusDays(7))
                    .total((long) (order.getTotal() * quotation.getPercentageStage1())/100)
                    .status(PaymentStatus.PENDING)
                    .build();
            paymentOrderRepository.save(paymentOrder);
        } else {
            quotation.setQuotationStatus(QuotationStatus.REJECTED);
            quotationRepository.save(quotation);
        }
        return StatusOfQuotationOrDesign.<QuotationStatus>builder()
                .id(quotation.getQuotationId())
                .status(quotation.getQuotationStatus())
                .build();
    }

    public ViewPaymentResponse viewPaymentConstruction(String constructionOrderId, String accountId) {
        Customer customer = customerRepository.findByAccountId(accountId).orElseThrow();
        List<PaymentOrder> paymentOrders = paymentOrderRepository.findByOrderId(constructionOrderId);
        List<PaymentInfoResponse> paymentInfoResponses = new ArrayList<>();
        for (PaymentOrder paymentOrder : paymentOrders) {
            PaymentInfoResponse response = PaymentInfoResponse.builder()
                    .paymentId(paymentOrder.getPaymentId())
                    .paidDate(paymentOrder.getPaidDate())
                    .dueDate(paymentOrder.getDueDate())
                    .price(paymentOrder.getTotal())
                    .paymentTitle(paymentOrder.getPaymentTitle())
                    .paymentStatus(paymentOrder.getStatus())
                    .build();
            paymentInfoResponses.add(response);
        }
        return ViewPaymentResponse.builder()
                .customerName(customer.getFirstName() + " " + customer.getLastName())
                .phone(customer.getPhone())
                .address(customer.getAddress())
                .paymentInfoResponseList(paymentInfoResponses)
                .build();
    }

    public ViewPaymentResponse viewPayment(String constructionOrderId) {
        ConstructionOrder order = constructOrderRepository.findById(constructionOrderId).orElseThrow();
        Customer customer = customerRepository.findById(order.getCustomerId()).orElseThrow();
        List<PaymentOrder> paymentOrders = paymentOrderRepository.findByOrderId(constructionOrderId);
        List<PaymentInfoResponse> paymentInfoResponses = new ArrayList<>();
        for (PaymentOrder paymentOrder : paymentOrders) {
            if (LocalDateTime.now().isAfter(paymentOrder.getDueDate())) {
                paymentOrder.setStatus(PaymentStatus.FAILED);
                paymentOrderRepository.save(paymentOrder);
            }
            PaymentInfoResponse response = PaymentInfoResponse.builder()
                    .paymentId(paymentOrder.getPaymentId())
                    .paidDate(paymentOrder.getPaidDate())
                    .dueDate(paymentOrder.getDueDate())
                    .price(paymentOrder.getTotal())
                    .paymentTitle(paymentOrder.getPaymentTitle())
                    .paymentStatus(paymentOrder.getStatus())
                    .build();
            paymentInfoResponses.add(response);
        }
        return ViewPaymentResponse.builder()
                .customerName(customer.getFirstName() + " " + customer.getLastName())
                .phone(customer.getPhone())
                .address(customer.getAddress())
                .paymentInfoResponseList(paymentInfoResponses)
                .build();
    }


    public ViewProgressResponse viewProgress(String constructionOrderId) {
        ConstructionOrder order = constructOrderRepository.findById(constructionOrderId).orElseThrow();
        List<ListConstructProgressResponse> listConstructProgressResponses = new ArrayList<>();
        List<ConstructionTasks> constructionTasks = constructionTasksRepository.findByConstructionOrderId(constructionOrderId).orElseThrow();
        for (ConstructionTasks constructionTask : constructionTasks) {
            String content = packageConstructionRepository.findById(constructionTask.getPackageConstructionId()).orElseThrow().getContent();
            ListConstructProgressResponse response = ListConstructProgressResponse.builder()
                    .packageConstructionId(constructionTask.getPackageConstructionId())
                    .taskId(constructionTask.getTaskId())
                    .startDate(constructionTask.getStartDate())
                    .endDate(constructionTask.getEndDate())
                    .content(content)
                    .status(constructionTask.getStatus())
                    .build();
            listConstructProgressResponses.add(response);
        }
        List<String> staffNames = constructionTaskStaffRepository.findStaffNamesByTaskId(constructionTasks.getFirst().getTaskId());
        return ViewProgressResponse.builder()
                .constructionOrderId(constructionOrderId)
                .listConstructProgressResponses(listConstructProgressResponses)
                .staffNames(staffNames)
                .status(order.getStatus())
                .build();
    }


    public ConstructionOrderStatus finishConstructOrder(String constructionOrderId, FinishConstructRequest request, String accountId) {
        ConstructionOrder order = constructOrderRepository.findById(constructionOrderId).orElseThrow();
        order.setStatus(request.getStatus());
        constructOrderRepository.save(order);
        Customer customer = customerRepository.findByAccountId(accountId).orElseThrow();
        List<PaymentOrder> paymentOrders = paymentOrderRepository.findByOrderId(constructionOrderId);
        long total = (long) order.getTotal();
        for (PaymentOrder paymentOrder : paymentOrders) {
            total -= paymentOrder.getTotal();
        }
        PaymentOrder paymentOrder = PaymentOrder.builder()
                .orderId(constructionOrderId)
                .customerId(customer.getCustomerId())
                .paymentTitle("Payment of the final stage")
                .paidDate(LocalDateTime.now())
                .dueDate(LocalDateTime.now().plusDays(7))
                .total(total)
                .status(PaymentStatus.PENDING)
                .build();
        paymentOrderRepository.save(paymentOrder);
        customer.setPoint((long) (order.getTotal() / 1000000));
        return order.getStatus();
    }


}
