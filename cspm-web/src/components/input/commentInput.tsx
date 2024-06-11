'use client'

import { useInputStore } from '@/components/Zustand/accountPageStore'

export default function CommentInput() {
  const { inputComment, setInputComment } = useInputStore()
  const handleNoteChange = (e: any) => {
    setInputComment(e.target.value)
  }

  return (
    <input
      id='comment-input'
      name='comment-input'
      className='mx-2 h-[80%] w-[50%] px-2'
      value={inputComment}
      onChange={handleNoteChange}
      placeholder='내용을 입력하세요'
    ></input>
  )
}
