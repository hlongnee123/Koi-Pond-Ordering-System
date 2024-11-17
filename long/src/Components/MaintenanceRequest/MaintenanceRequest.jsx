import React, { useEffect, useState } from "react";
import axios from "axios";
import { toast, ToastContainer } from "react-toastify";
import { jwtDecode } from "jwt-decode";

const MaintenanceRequest = () => {
    const [maintenanceRequests, setMaintenanceRequests] = useState([]);
    const [constructorList, setConstructorList] = useState([]);

    // lay du lieu constructor staff tu backend
    const fetchConstructors = async () => {
        try {
            const response = await axios.get('http://localhost:8080/maintenance/constructors', {
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem('token')}`,
                }
            });
            setConstructorList(response.data.data);
        } catch (error) {
            console.error('Error fetching constructors:', error);
            toast.error('Failed to load constructors. ^^');
        }
    };
    const fetchRequests = async () => {
        try {
            const response = await axios.get('http://localhost:8080/maintenance/requests', {
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem('token')}`,
                }
            });
            setMaintenanceRequests(response.data.data);
        } catch (error) {
            console.error('Error fetching requests:', error);
            toast.error('Failed to load requests. ^^');
        }
    }
    // lay du lieu request tu backend
    useEffect(() => {

        fetchRequests()
        fetchConstructors()
    }, []);

    const formatDate = (dateString) => {
        if (!dateString) return "";
        const date = new Date(dateString);
        const day = String(date.getDate()).padStart(2, "0");
        const month = String(date.getMonth() + 1).padStart(2, "0");
        const year = date.getFullYear();
        return `${day}/${month}/${year}`;
    };

    //handle staff assignment
    const handleAssignStaff = async (maintenanceOrderId, staffType, staffId) => {
        try {
            // xac dinh trang thai moi dua tren loai nhan vien
            let newStatus;
            switch (staffType) {
                case "constructorLeaderId":
                    newStatus = "MAINTAINING";
                    break;
                default:
                    newStatus = "REQUESTED"; // Trạng thái mặc định
                    break;
            }

            await axios.put('http://localhost:8080/maintenance/requests', {
                maintenanceOrderId,
                [staffType]: staffId,
                status: newStatus
            }, {
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem('token')}`, // Attach token
                }
            });
            setMaintenanceRequests(prevRequests =>
                prevRequests.map(request =>
                    request.orderId === maintenanceOrderId
                        ? { ...request, [staffType]: staffId, status: newStatus } : request
                )
            );

            toast.success("Assign staff and status successfully!");
        } catch (error) {
            console.error('Error assigning staff:', error);
            toast.error("Failed to assign staff or status. Please try again.");
        }
    };

    // //handle status change
    // const handleStatusChange = async (statusTab) => {
    //     try {
    //         const respone = await axios.get(`http://localhost:8080/manager/requests/${statusTab}`);
    //         setRequests(respone.data)
    //     } catch (error) {
    //         console.error('Error fetching Status:', error);
    //         toast.error('Failed to load Status. ^^');
    //     }

    // }
    // Định nghĩa icon dựa theo status
    const getStatusIcon = (status) => {
        switch (status) {
            case "MAINTAINING":
                return { icon: <i className="fa-solid fa-wrench" title="Constructing" style={{ marginRight: '5px' }}></i>, className: "text-warning" };
            case "REQUESTED":
            default:
                return { icon: <i className="fas fa-info-circle" title="Requested" style={{ marginRight: '5px' }}></i>, className: "text-secondary" };
        }
    };
    const handleCreatePayment = async (totalPrice, orderId) => {
        const token = localStorage.getItem('token');
        const decoded = jwtDecode(token)
        const accountId = decoded.sub
        try {
            await axios.post(`http://localhost:8080/maintenance/${orderId}/createPayment`, {
                totalPrice, orderId, accountId
            }, {
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem('token')}`,
                },

            });
            fetchRequests()
            toast.success("POST SUCCESS !! ^^");
        } catch (error) {
            console.error('Error fetching constructors:', error);
            toast.error('Failed to load constructors. ^^');
        }
    };


    return (
        <div className="container-fuild mt-4">
            <ToastContainer position="top-right" autoClose={5000} hideProgressBar={false} closeOnClick pauseOnFocusLoss draggable pauseOnHover />
            <h2 className="text-center">Manage Mantenance Requests</h2>
            {/* <div>
                <nav className="nav justify-content-center mb-2">
                    <div className="nav-item">
                        <button className="btn btn-outline-primary mx-1" onClick={() => handleStatusChange("all")}>All</button>
                    </div>
                    <div className="nav-item">
                        <button className="btn btn-outline-primary mx-1" onClick={() => handleStatusChange("requested")}>Requested</button>
                    </div>
                    <div className="nav-item">
                        <button className="btn btn-outline-primary mx-1" onClick={() => handleStatusChange("consulting")}>Consulting</button>
                    </div>
                    <div className="nav-item">
                        <button className="btn btn-outline-primary mx-1" onClick={() => handleStatusChange("designing")}>Designing</button>
                    </div>
                    <div className="nav-item">
                        <button className="btn btn-outline-primary mx-1" onClick={() => handleStatusChange("constructing")}>Constructing</button>
                    </div>
                    <div className="nav-item">
                        <button className="btn btn-outline-primary mx-1" onClick={() => handleStatusChange("completed")}>Complete</button>
                    </div>
                </nav>
            </div> */}
            <table className="table table-bordered ">
                <thead>
                    <tr>
                        <th scope="col" className="text-center">Customer</th>
                        <th scope="col" className="text-center">Phone</th>
                        <th scope="col" className="text-center">Address</th>
                        <th scope="col" className="text-center">Total Price</th>
                        <th scope="col" className="text-center">Constructor</th>
                        <th scope="col" className="text-center">Status</th>
                        <th scope="col" className="text-center">Create Payment</th>
                    </tr>
                </thead>
                <tbody>
                    {maintenanceRequests.length === 0 ? (
                        <tr>
                            <td colSpan="10" className="text-center">
                                Your system has no Request ^^ !
                            </td>
                        </tr>
                    ) : (
                        maintenanceRequests.map(request => (
                            <tr key={request.orderId} className="text-center align-item-center align-content-center">
                                <td className="text-center align-content-center col-1">  <i className="fas fa-user" style={{ marginRight: '5px' }} />{request.customerName}</td>
                                <td className="text-center align-content-center">{request.phone}</td>
                                <td className="text-center align-content-center col-2">{request.address}</td>
                                <td className="text-center align-content-center">{request.totalPrice.toLocaleString()}</td>

                                <td>
                                    <select
                                        className="form-select mt-2"
                                        onChange={(e) => {
                                            handleAssignStaff(request.orderId, e.target.name, e.target.value)
                                        }}
                                        value={request.constructorLeaderId ? request.constructorLeaderId : ""}
                                        name="constructorLeaderId"
                                    >
                                        <option value="" disabled>Select Constructor</option>
                                        {constructorList.map(staff => (
                                            <option key={staff.staffId} value={staff.staffId}>
                                                {staff.staffName}
                                            </option>
                                        ))}
                                    </select>
                                </td>
                                <td className={`text-center align-content-center col-2 ${getStatusIcon(request.status).className}`}>
                                    {getStatusIcon(request.status).icon} {request.status}
                                </td>
                                <td className="text-center align-content-center">
                                    {request.status === "MAINTAINED" ? (
                                        <button
                                            className="btn btn-danger"
                                            onClick={() => handleCreatePayment(request.totalPrice, request.orderId)}
                                        >
                                            CREATE PAYMENT
                                        </button>) : (null)
                                    }

                                </td>
                            </tr>
                        ))
                    )}
                </tbody>
            </table>
        </div>
    );
};

export default MaintenanceRequest;

