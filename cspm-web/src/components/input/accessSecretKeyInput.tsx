'use client'

import { useInputStore } from '@/components/Zustand/accountPageStore'

export default function AssessSecretKeyInput() {
  const { inputAccessSecretKey, setInputAccessSecretKey } = useInputStore()
  const handleSecretKeyChange = (e: any) => {
    setInputAccessSecretKey(e.target.value)
  }
  return (
    <input
      id='accessse-cretkey-input'
      name='access-secretekey-input'
      className='mx-2 h-[80%] w-[50%] px-2'
      value={inputAccessSecretKey}
      onChange={handleSecretKeyChange}
      placeholder='내용을 입력하세요'
    ></input>
  )
}
