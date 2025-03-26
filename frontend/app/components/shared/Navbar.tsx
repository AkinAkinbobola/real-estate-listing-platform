import {Button} from "@/components/ui/button";

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
        <nav className={"justify-end flex gap-12 items-center"}>
            {
                navLinks.map(navLink => (
                    <div key={navLink.name}>
                        {navLink.name}
                    </div>
                ))
            }

            <Button>
                Join / Sign In
            </Button>
        </nav>
    );
};

export default Navbar;