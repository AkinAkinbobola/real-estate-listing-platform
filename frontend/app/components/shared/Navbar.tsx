import {Button} from "@/components/ui/button";
import {Menu} from "lucide-react";

const navLinks = [
    {
        name: "Browse",
    },
    {
        name: "List"
    },
    {
        name: "Resources"
    },
    {
        name: "Professionals"
    }
]

const Navbar = () => {
    return (
        <nav className={"flex justify-between md:justify-end items-center gap-12"}>
            <div className={"gap-12 items-center hidden md:flex"}>
                {
                    navLinks.map(navLink => (
                        <div key={navLink.name}>
                            {navLink.name}
                        </div>
                    ))
                }
            </div>

            <Menu className={"block md:hidden"}/>

            <Button>
                Join / Sign In
            </Button>
        </nav>
    );
};

export default Navbar;