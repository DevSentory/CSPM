'use client'

import { useInputStore } from '@/components/Zustand/accountPageStore'

export default function CodeInput() {
  const { inputCode, setInputCode } = useInputStore()
  const handeleInputChange = (e: any) => {
    const regex = /^[a-zA-Z]*$/
    if (regex.test(e.target.value)) {
      setInputCode(e.target.value)
    }
  }
  return (
    <input
      id='code-input'
      name='code-input'
      className='mx-2 h-[80%] w-[50%] px-2'
      placeholder='내용을 입력하세요'
      type='text'
      value={inputCode}
      maxLength={5}
      onChange={handeleInputChange}
    ></input>
  )
}
