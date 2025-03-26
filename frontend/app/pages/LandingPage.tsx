import type {Route} from "../+types/root";
import Navbar from "@/components/shared/Navbar";
import Hero from "@/components/shared/Hero";

export function meta({}: Route.MetaArgs) {
    return [
        {title: "NextGen Realty"},
        {
            name: "description",
            content: "NextGen Realty makes home buying, selling, and renting simple with real-time listings, smart search tools, and expert guidance."
        },
    ];
}

const LandingPage = () => {
    return (
        <main className={"py-10"}>
            <div className={"px-[80px] space-y-40"}>
                <Navbar/>

                <Hero/>
            </div>
        </main>
    );
};

export default LandingPage;