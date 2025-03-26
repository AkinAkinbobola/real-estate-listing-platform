import type {Route} from "../+types/root";
import Navbar from "@/components/shared/Navbar";

export function meta({}: Route.MetaArgs) {
    return [
        {title: "HomeEasy - Find Your Perfect Home, Effortlessly"},
        {
            name: "description",
            content: "HomeEasy makes home buying, selling, and renting simple with real-time listings, smart search tools, and expert guidance."
        },
    ];
}

const LandingPage = () => {
    return (
        <main className={"container py-10"}>
            <Navbar/>
        </main>
    );
};

export default LandingPage;