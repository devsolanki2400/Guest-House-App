import React from "react";
import { Col, Container, Row } from "react-bootstrap";

const Footer = () => {
    const today = new Date();

    return (
        <footer className="bg-dark text-light py-4 mt-5">
            <Container>
                <Row className="text-center text-md-start">
                    {/* Support */}
                    <Col xs={12} md={3}>
                        <h6 className="text-uppercase fw-bold">Support</h6>
                        <ul className="list-unstyled">
                            <li>Contact Us</li>
                            <li>Help Center</li>
                            <li>Booking Policies</li>
                            <li>FAQs</li>
                        </ul>
                    </Col>

                    {/* Discover */}
                    <Col xs={12} md={3}>
                        <h6 className="text-uppercase fw-bold">Discover</h6>
                        <ul className="list-unstyled">
                            <li>Offers & Deals</li>
                            <li>Nearby Attractions</li>
                            <li>Top Destinations</li>
                            <li>Reviews</li>
                        </ul>
                    </Col>

                    {/* Terms */}
                    <Col xs={12} md={3}>
                        <h6 className="text-uppercase fw-bold">Legal</h6>
                        <ul className="list-unstyled">
                            <li>Privacy Policy</li>
                            <li>Terms & Conditions</li>
                            <li>Cancellation Policy</li>
                        </ul>
                    </Col>

                    {/* About */}
                    <Col xs={12} md={3}>
                        <h6 className="text-uppercase fw-bold">About</h6>
                        <ul className="list-unstyled">
                            <li>Our Story</li>
                            <li>Careers</li>
                            <li>Contact</li>
                        </ul>
                    </Col>
                </Row>

                <hr className="border-light my-3" />

                <Row>
                    <Col className="text-center">
                        <p className="mb-0">&copy; {today.getFullYear()} Guest House Booking App. All Rights Reserved.</p>
                    </Col>
                </Row>
            </Container>
        </footer>
    );
};

export default Footer;
