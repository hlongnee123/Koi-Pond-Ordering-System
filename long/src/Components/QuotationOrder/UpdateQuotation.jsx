import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import axios from "axios";
import 'bootstrap/dist/css/bootstrap.min.css';
import styles from './QuotationOrder.module.css';
import { jwtDecode } from "jwt-decode";

const UpdateQuotation = () => {
  const { quotationId } = useParams()
  const [quotation, setQuotation] = useState(null);
  const [loading, setLoading] = useState(true);
  const [packageOptions, setPackageOptions] = useState([]);
  const [length, setLength] = useState('');
  const [width, setWidth] = useState('');
  const [height, setHeight] = useState('');
  const [selectedPackage, setSelectedPackage] = useState('');
  const [items, setItems] = useState([]);
  const [itemsOfSelectedPackage, setItemsOfSelectedPackage] = useState([]); // State để lưu danh sách item từ package
  const [endDate, setEndDate] = useState('');
  const [startDate, setStartDate] = useState('');
  const [promotionId, setPromotionId] = useState('');
  const [customerRequest, setCustomerRequest] = useState('');
  const [percentageStage1, setPercentageStage1] = useState('');
  const [percentageStage2, setPercentageStage2] = useState('');
  const [percentageStage3, setPercentageStage3] = useState('');
  const navigate = useNavigate();

  useEffect(() => {

    const token = localStorage.getItem('token')
    const decode = jwtDecode(token)
    const accountId = decode.sub

    const fetchQuotationOrder = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/staffs/${accountId}/rejectedQuotations/${quotationId}`, {
          headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`, // Attach token
          }
        });

        setQuotation(response.data.data);
        setCustomerRequest(response.data.data.customerRequest);
        setWidth(response.data.data.width);
        setHeight(response.data.data.height);
        setLength(response.data.data.length);
        setSelectedPackage(response.data.data.packageId);
        setStartDate(response.data.data.startDate.split('T')[0]);
        setEndDate(response.data.data.endDate.split('T')[0]);
        setPercentageStage1(response.data.data.percentageStage1)
        setPercentageStage2(response.data.data.percentageStage2)
        setPercentageStage3(response.data.data.percentageStage3)
        setLoading(false);
      } catch (error) {
        console.error("Error fetching quotation order:", error);
        setLoading(false);
      }
    };

    const fetchPackageOptions = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/packages`, {
          headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`, // Attach token
          }
        });
        setPackageOptions(response.data.data.packagesList);
        setItems(response.data.data.packageConstructionList);
      } catch (error) {
        console.error("Error fetching package options:", error);
      }
    };
    fetchQuotationOrder();
    fetchPackageOptions();
  }, [quotationId]);

  useEffect(() => {
    getItems(quotation?.packageId);
  }, [quotation])

  const getItems = (packageId) => {
    const filteredItems = items.filter(item => item.packageId === packageId);
    setItemsOfSelectedPackage(filteredItems);
  };

  const handlePackageChange = (event) => {
    const packageId = event.target.value;
    setSelectedPackage(packageId);
    if (packageId) {
      getItems(packageId); // Gọi hàm lấy items khi package thay đổi
    } else {
      setItemsOfSelectedPackage([]); // Xóa danh sách items nếu không có package nào được chọn
    }
  };

  const UpdateQuotation = async () => {
    const requestData = {
      packageId: selectedPackage,
      length,
      height,
      width,
      customerRequest,
      percentageStage1,
      percentageStage2,
      percentageStage3,
      startDate,
      endDate,
      promotionId,
    };

    const token = localStorage.getItem('token')
    const decode = jwtDecode(token)
    const accountId = decode.sub

    try {
      await axios.put(`http://localhost:8080/staffs/${accountId}/rejectedQuotations/${quotationId}`, requestData, {
        headers: {
          'Authorization': `Bearer ${localStorage.getItem('token')}`, // Attach token
        }
      });
      navigate(`/consult/ownedTasks/${quotation.constructionOrderId}/quotation`);
    } catch (error) {
      console.error("Error submitting quotation data:", error);
    }
  };

  if (loading) {
    return <div className="text-center py-5">Loading...</div>;
  }

  if (!quotation) {
    return <div className="text-center py-5">No quotation data found.</div>;
  }

  return (
    <div className={`${styles.quotationOrderContainer} container mt-5`}>
      <div className="card shadow">
        <div className={`card-header text-center ${styles.bgRed} text-white`}>
          <h2 className="text-center">Quotation</h2>
          <div className="d-flex justify-content-between">
            <p><strong>Order ID:</strong> {quotation.constructionOrderId}</p>
            <p><strong>Consultant:</strong> {quotation.consultantName}</p>
          </div>
        </div>
        <div className="card-body">
          <h3>Information customer:</h3>
          <div className="row mb-3">
            <div className="col-md-6">
              <p><strong>Customer: </strong> {quotation.customerName}</p>
            </div>
            <div className="col-md-6">
              <p><strong>Phone: </strong> {quotation.phone}</p>
            </div>
            <div className="col-md-6">
              <p><strong>Address: </strong> {quotation.address}</p>
            </div>
            <div className="col-md-12">
              <p><strong>Request: </strong></p>
              <textarea
                className="form-control"
                id="customerRequest"
                value={customerRequest}
                onChange={(e) => setCustomerRequest(e.target.value)}
                placeholder={quotation.customerRequest}
                rows="6" // Adjust the number of rows to set the height of the textarea
              />
            </div>
          </div>
          <h3>Quotation:</h3>
          <div className="row mb-3">
            <div className="col-md-6">
              <label htmlFor="length"><strong>Length:</strong></label>
              <input
                type="text"
                className="form-control"
                id="length"
                value={length}
                onChange={(e) => setLength(e.target.value)}
              />
            </div>
            <div className="col-md-6">
              <label htmlFor="width"><strong>Width:</strong></label>
              <input
                type="text"
                className="form-control"
                id="width"
                value={width}
                onChange={(e) => setWidth(e.target.value)}
              />
            </div>
          </div>
          <div className="row mb-3">
            <div className="col-md-12">
              <label htmlFor="height"><strong>Height:</strong></label>
              <input
                type="text"
                className="form-control"
                id="height"
                value={height}
                onChange={(e) => setHeight(e.target.value)}
              />
            </div>
          </div>
          <div className="row mb-3">
            <div className="col-md-12 d-flex align-items-end">
              <label htmlFor="percentageStage1" className="mr-2"><strong>Customer agrees to paid for first pharse (%): </strong></label>
              <input
                style={{ width: 80 }}
                type="number"
                className="form-control"
                id="percentageStage1"
                value={percentageStage1}
                onChange={(e) => setPercentageStage1(e.target.value)}
                placeholder="Percentage"
              />
            </div>
          </div>
          <div className="row mb-3">
            <div className="col-md-12 d-flex align-items-end">
              <label htmlFor="percentageStage2" className="mr-2"><strong>Customer agrees to paid for second pharse (%): </strong></label>
              <input
                style={{ width: 80 }}
                type="number"
                className="form-control"
                id="percentageStage2"
                value={percentageStage2}
                onChange={(e) => setPercentageStage2(e.target.value)}
                placeholder="Percentage"
              />
            </div>
          </div>
          <div className="row mb-5">
            <div className="col-md-12 d-flex align-items-end">
              <label htmlFor="percentageStage3" className="mr-2"><strong>Customer agrees to paid for finally pharse (%): </strong></label>
              <input
                style={{ width: 80 }}
                type="number"
                className="form-control"
                id="percentageStage3"
                value={percentageStage3}
                onChange={(e) => setPercentageStage3(e.target.value)}
                placeholder="Percentage"
              />
            </div>
          </div>
          <div className="row mb-3">
            <div className="col-md-12">
              <label htmlFor="package"><strong>Package:</strong></label>
              <select
                className="form-control"
                id="package"
                value={selectedPackage}
                onChange={handlePackageChange}
              >
                <option value="">Select Package</option>
                {packageOptions.map((pkg) => (
                  <option key={pkg.packageId} value={pkg.packageId}>
                    {pkg.packageType}
                  </option>
                ))}
              </select>
            </div>
          </div>
          <h3>Construction contents:</h3>
          {itemsOfSelectedPackage.map((item, index) => (
            <div key={index}>
              <label>{item.content}</label>
            </div>
          ))}
          <div className="row mb-3">
            <div className="col-md-6">
              <label htmlFor="startDate"><strong>Start Date:</strong></label>
              <input
                type="date"
                className="form-control"
                id="startDate"
                value={startDate}
                onChange={(e) => setStartDate(e.target.value)}
              />
            </div>
            <div className="col-md-6">
              <label htmlFor="endDate"><strong>End Date:</strong></label>
              <input
                type="date"
                className="form-control"
                id="endDate"
                value={endDate}
                onChange={(e) => setEndDate(e.target.value)}
              />
            </div>
          </div>
          <div className="d-flex justify-content-end">
            <button className="btn btn-success" onClick={UpdateQuotation}>Update Quotation</button>
          </div>
        </div>
      </div>
    </div>

  );
};

export default UpdateQuotation;

