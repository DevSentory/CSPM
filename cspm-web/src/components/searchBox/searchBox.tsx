'use client'

import { useSearchStore } from '@/components/Zustand/store'
import { useEffect } from 'react'

export default function SearchBox() {
  const { baseSearcheValue, setBaseSearcheValue } = useSearchStore()
  const handleSearch = (e: any) => {
    setBaseSearcheValue(e.target.value)
  }
  useEffect(() => {})
  return (
    <div className='flex h-10 w-full items-center border-2 bg-white text-black '>
      <span className='flex h-9 w-[20%] items-center justify-center bg-gray-200'>검색</span>
      <input
        id='search-input'
        name='search-input'
        className='h-9 w-[80%] p-2'
        placeholder='조회 텍스트를 입력하세요'
        value={baseSearcheValue}
        onChange={handleSearch}
      ></input>
    </div>
  )
}
