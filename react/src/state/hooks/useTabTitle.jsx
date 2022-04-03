import { useEffect } from 'react';

export default function useTabTitle(title) {
    useEffect(() => {
        document.title = title;
    }, [title]);
}