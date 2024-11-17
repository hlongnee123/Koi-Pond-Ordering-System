import React, { useEffect, useState } from 'react';
import './Contact.css';
import Navbar from '../Navbar/Navbar';
import Footer from '../Footer/Footer';
import logo from '../Assests/logo-navbar.png'
import axios from 'axios';
import { toast, ToastContainer } from 'react-toastify';

const Contact = () => {

    const [service, setService] = useState('');
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [address, setAddress] = useState('');
    const [phone, setPhone] = useState('');
    const [customerRequest, setCustomerRequest] = useState('');
    const [customerInformation, setCustomerInformation] = useState({});

    // const [file, setFile] = useState(null);
    const [submitted, setSubmitted] = useState(false);
    const [error, setError] = useState('');



    useEffect(() => {
        const fetchCustomerInformation = async () => {
            try {
                const response = await axios.get('http://localhost:8080/customerInfo', {
                    headers: {
                        'Authorization': `Bearer ${localStorage.getItem('token')}`,
                    }
                });
                setCustomerInformation(response.data.data)
                toast.success("Fetch information successfully ^^")
            } catch (error) {
                toast.error("Fetch infomation ERROR !!")
                console.error("Fail to fetch INFOR! ^^")
            }
        }
        fetchCustomerInformation()
    }, [])





    const handleSubmit = async (event) => {
        event.preventDefault(); // ngan chan reload
        setError('');
        setSubmitted(false);
        try {
            const response = await axios.post('http://localhost:8080/contact', {
                service: service,
                firstName: firstName || customerInformation.firstName,
                lastName: lastName || customerInformation.lastName,
                phone: phone || customerInformation.phone,
                address: address,
                customerRequest: customerRequest
            },
                {
                    headers: {
                        'Authorization': `Bearer ${localStorage.getItem('token')}`, // Attach token
                    }
                }
            );
            console.log(response.data);

            if (response.status === 200) {
                setSubmitted(true);
            }

        } catch (error) {
            setError('FAIL, please try again !!');
            console.error(error);
        }

    };

    return (
        <>
            <Navbar />

            <div className="container mb-5">
                <ToastContainer position="top-right" autoClose={5000} hideProgressBar={false} closeOnClick pauseOnFocusLoss draggable pauseOnHover />

                <h1 className="text-center my-4" style={{ color: 'red' }}>Contact us</h1>
                <div className="row">
                    <div className="col-md-6" style={{ marginLeft: '25%' }}>
                        {/* check xem la form dc submit hong */}
                        {submitted ? (
                            // SUCCESS !!!
                            <div className="notification alert-success" style={{ marginLeft: '110px' }}>
                                <div className="success-icon">
                                    <i className="fa-solid fa-check"></i>
                                </div>
                                <div className="success-text">
                                    SUBMIT SUCCESSFULLY !
                                </div>
                            </div>


                        ) : ( // FAIL !
                            <form onSubmit={handleSubmit} className="bg-light p-4 rounded shadow-lg" >
                                {error && <div className="notification-error alert-danger">{error}</div>}
                                <div className="text-center label-upload">
                                    <label> Contact Form </label>
                                </div>

                                <div className="form-group mb-6">

                                    <label>Select Service</label>
                                    <select
                                        name="service"
                                        className="form-control"
                                        value={service}
                                        onChange={(event) => setService(event.target.value)}
                                        required
                                    >
                                        <option value="">-- Choose a Service -- </option>
                                        <option value="MAINTENANCE_SERVICE">Maintenance</option>
                                        <option value="CONSTRUCTION_SERVICE">Build Koi Pond</option>
                                    </select>
                                </div>
                                <div className="form-group mb-6">
                                    <label>First Name</label>
                                    <input type="text" className="form-control" name="firstName"
                                        value={customerInformation.firstName}
                                        onChange={(event) => setFirstName(event.target.value)}
                                        required />
                                </div>

                                <div className="form-group mb-6">
                                    <label>Last Name</label>
                                    <input type="text" className="form-control" name="lastName"
                                        value={customerInformation.lastName}
                                        onChange={(event) => setLastName(event.target.value)}
                                        placeholder={customerInformation.lastName}
                                        required />
                                </div>

                                <div className="form-group mb-6">
                                    <label>Phone</label>
                                    <input
                                        type="text"
                                        className="form-control"
                                        name="phone"
                                        value={customerInformation.phone}
                                        onChange={(event) => setPhone(event.target.value)}
                                        required
                                    />
                                </div>

                                <div className="form-group mb-6">
                                    <label>Address</label>
                                    <input type="text" className="form-control" name="address"
                                        value={address}
                                        onChange={(event) => setAddress(event.target.value)}
                                        required />
                                </div>



                                <div className="form-group mb-6">
                                    <label>Detail</label>
                                    <textarea
                                        className="form-control"
                                        name="customerRequest"
                                        rows="4"
                                        value={customerRequest}
                                        onChange={(event) => setCustomerRequest(event.target.value)}
                                        required
                                    ></textarea>
                                </div>

                                <button type="submit" className="btn btn-danger py-3 w-100 fw-boild py-1 mt-3">
                                    Submit request
                                </button>
                            </form>
                        )}
                    </div>


                </div >
            </div>

            {/* Footer ở cuối trang */}
            <Footer />
        </>
    );

};

export default Contact;
