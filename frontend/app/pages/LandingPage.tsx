import type {Route} from "../+types/root";

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
        <div>
            LandingPage
        </div>
    );
};

export default LandingPage;