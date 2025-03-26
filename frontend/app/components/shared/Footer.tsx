const Footer = () => {
    return (
        <footer className={"flex justify-between"}>
            <h1>Nexus</h1>

            <div className={"flex gap-10"}>
                <div className={"flex flex-col gap-3"}>
                    <h1>Nexus</h1>

                    <p>About Us</p>
                    <p>Careers</p>
                    <p>Contact</p>
                </div>

                <div className={"flex flex-col gap-3"}>
                    <div>Quicklinks</div>
                    <div>Create Account</div>
                    <div>Browse Listings</div>
                    <div>Agents</div>
                </div>

                <div className={"flex flex-col gap-3"}>
                    <div>Legal</div>
                    <div>Privacy Policy</div>
                    <div>Terms of Service</div>
                    <div>Copyright</div>
                </div>
            </div>
        </footer>
    );
};

export default Footer;